package com.example.linksaveai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linksaveai.data.AnalysisRequest
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.data.RetrofitClient
import com.example.linksaveai.service.SharedLinkHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LinkSafeViewModel : ViewModel() {

    init {
        SharedLinkHolder.listener = { url, source ->
            handleSharedOrCapturedUrl(url, source)
        }
    }

    private val _currentUrl = MutableStateFlow("")
    val currentUrl: StateFlow<String> = _currentUrl.asStateFlow()

    private val _selectedScanResult = MutableStateFlow<LinkItem?>(null)
    val selectedScanResult: StateFlow<LinkItem?> = _selectedScanResult.asStateFlow()

    private val _scanHistory = MutableStateFlow<List<LinkItem>>(
        listOf(
            LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true, "Valid SSL certificate, trusted domain."),
            LinkItem("2", "http://free-prize-win.xyz/login", "free-prize-win.xyz", "Suspicious", "Yesterday, 4:15 PM", false, "Unencrypted HTTP, suspicious top-level domain (.xyz)."),
            LinkItem("3", "https://github.com", "github.com", "Safe", "Oct 24, 2:00 PM", true, "Verified developer platform, trusted HTTPS.")
        )
    )
    val scanHistory: StateFlow<List<LinkItem>> = _scanHistory.asStateFlow()

    private val _savedLinks = MutableStateFlow<List<LinkItem>>(
        _scanHistory.value.filter { it.isSaved }
    )
    val savedLinks: StateFlow<List<LinkItem>> = _savedLinks.asStateFlow()

    fun updateCurrentUrl(url: String) {
        _currentUrl.value = url
    }

    fun scanUrl(url: String, onResult: (LinkItem) -> Unit) {
        val trimmed = url.trim()
        val formattedUrl = if (trimmed.startsWith("http")) trimmed else "https://$trimmed"
        val domain = try {
            val uri = android.net.Uri.parse(formattedUrl)
            uri.host ?: trimmed
        } catch (e: Exception) {
            trimmed
        }

        val timeStr = SimpleDateFormat("MMM dd, h:mm a", Locale.getDefault()).format(Date())

        val tempItem = LinkItem(
            id = System.currentTimeMillis().toString(),
            url = formattedUrl,
            domain = domain,
            status = "Unable to Verify",
            timestamp = timeStr,
            isSaved = false,
            details = "Analyzing with ML model..."
        )

        _selectedScanResult.value = tempItem
        _scanHistory.update { listOf(tempItem) + it }
        _savedLinks.value = _scanHistory.value.filter { it.isSaved }
        onResult(tempItem)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.analyzeUrl(AnalysisRequest(formattedUrl))
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    val updatedItem = tempItem.copy(
                        status = result.status,
                        details = "${result.explanation} (Confidence: ${(result.confidence * 100).toInt()}%)"
                    )
                    _selectedScanResult.value = updatedItem
                    _scanHistory.update { list -> list.map { if (it.id == updatedItem.id) updatedItem else it } }
                    _savedLinks.value = _scanHistory.value.filter { it.isSaved }
                    onResult(updatedItem)
                } else {
                    val errorItem = tempItem.copy(
                        status = "Unable to Verify",
                        details = "Server error or service unavailable."
                    )
                    _selectedScanResult.value = errorItem
                    _scanHistory.update { list -> list.map { if (it.id == errorItem.id) errorItem else it } }
                    _savedLinks.value = _scanHistory.value.filter { it.isSaved }
                    onResult(errorItem)
                }
            } catch (e: Exception) {
                val offlineItem = tempItem.copy(
                    status = "Unable to Verify",
                    details = "Network error / Backend offline. Unable to verify classification."
                )
                _selectedScanResult.value = offlineItem
                _scanHistory.update { list -> list.map { if (it.id == offlineItem.id) offlineItem else it } }
                _savedLinks.value = _scanHistory.value.filter { it.isSaved }
                onResult(offlineItem)
            }
        }
    }

    fun handleSharedOrCapturedUrl(url: String, source: String = "Notification") {
        val trimmed = url.trim()
        if (trimmed.isBlank()) return
        if (_scanHistory.value.any { it.url.equals(trimmed, ignoreCase = true) }) {
            return
        }

        val domain = try {
            val uri = android.net.Uri.parse(if (trimmed.startsWith("http")) trimmed else "https://$trimmed")
            uri.host ?: trimmed
        } catch (e: Exception) {
            trimmed
        }

        val timeStr = SimpleDateFormat("MMM dd, h:mm a", Locale.getDefault()).format(Date())

        val item = LinkItem(
            id = System.currentTimeMillis().toString(),
            url = if (trimmed.startsWith("http")) trimmed else "https://$trimmed",
            domain = domain,
            status = "Unable to Verify",
            timestamp = "$timeStr ($source)",
            isSaved = true,
            details = "Captured from $source. Awaiting ML analysis."
        )

        _scanHistory.update { listOf(item) + it }
        _savedLinks.value = _scanHistory.value.filter { it.isSaved }
    }

    fun toggleSaveLink(item: LinkItem) {
        _scanHistory.update { list ->
            list.map { if (it.id == item.id) it.copy(isSaved = !it.isSaved) else it }
        }
        _savedLinks.value = _scanHistory.value.filter { it.isSaved }
        if (_selectedScanResult.value?.id == item.id) {
            _selectedScanResult.value = _selectedScanResult.value?.copy(isSaved = !item.isSaved)
        }
    }

    fun clearHistory() {
        _scanHistory.value = emptyList()
        _savedLinks.value = emptyList()
    }
}
