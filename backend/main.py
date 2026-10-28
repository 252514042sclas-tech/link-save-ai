import os
import joblib
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import Optional

app = FastAPI(title="LinkSafe AI ML Backend", version="1.0")

MODEL_PATH = "phishing_model.joblib"
model = None
feature_extractor = None

if os.path.exists(MODEL_PATH):
    loaded = joblib.load(MODEL_PATH)
    if isinstance(loaded, tuple):
        model, feature_extractor = loaded
    else:
        model = loaded

class AnalysisRequest(BaseModel):
    url: str

class AnalysisResponse(BaseModel):
    url: str
    status: str  # "Safe", "Suspicious", "Unable to Verify"
    confidence: float
    explanation: str

@app.post("/analyze", response_model=AnalysisResponse)
def analyze_url(req: AnalysisRequest):
    url = req.url.strip()
    if not url:
        raise HTTPException(status_code=400, detail="URL cannot be empty")

    if model is None or feature_extractor is None:
        # Fallback if model isn't trained yet
        return AnalysisResponse(
            url=url,
            status="Unable to Verify",
            confidence=0.0,
            explanation="ML model weights are not loaded. Unable to verify classification."
        )

    try:
        feats = feature_extractor(url)
        X = [[
            feats['url_len'],
            feats['domain_len'],
            feats['has_ip'],
            feats['has_https'],
            feats['dot_count'],
            feats['slash_count'],
            feats['keyword_count']
        ]]

        prediction = model.predict(X)[0] # 0 = Safe, 1 = Suspicious
        proba = model.predict_proba(X)[0]
        confidence = float(max(proba))

        if prediction == 1:
            status = "Suspicious"
            explanation = f"Suspicious indicators detected (IP presence: {feats['has_ip']}, keyword matches: {feats['keyword_count']}, HTTPS: {feats['has_https']})."
        else:
            status = "Safe"
            explanation = f"Valid scheme, normal domain characteristics, low risk heuristic pattern."

        return AnalysisResponse(
            url=url,
            status=status,
            confidence=round(confidence, 2),
            explanation=explanation
        )
    except Exception as e:
        return AnalysisResponse(
            url=url,
            status="Unable to Verify",
            confidence=0.0,
            explanation=f"Analysis failed due to processing error: {str(e)}"
        )

@app.get("/")
def health_check():
    return {"status": "online", "model_loaded": model is not None}
