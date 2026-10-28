import re
import joblib
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.compose import ColumnTransformer
from urllib.parse import urlparse

def extract_features(url: str) -> dict:
    parsed = urlparse(url)
    domain = parsed.netloc or parsed.path
    path = parsed.path

    # Heuristic features
    url_len = len(url)
    domain_len = len(domain)
    has_ip = 1 if re.search(r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}', domain) else 0
    has_https = 1 if parsed.scheme == 'https' else 0
    dot_count = url.count('.')
    slash_count = url.count('/')

    suspicious_keywords = ['login', 'verify', 'update', 'free', 'win', 'account', 'secure', 'bank', 'xyz', 'crypto']
    keyword_count = sum(1 for kw in suspicious_keywords if kw in url.lower())

    return {
        'url': url,
        'url_len': url_len,
        'domain_len': domain_len,
        'has_ip': has_ip,
        'has_https': has_https,
        'dot_count': dot_count,
        'slash_count': slash_count,
        'keyword_count': keyword_count
    }

def train_model():
    # Sample training dataset (0 = Safe, 1 = Suspicious/Phishing)
    data = [
        ("https://google.com", 0),
        ("https://github.com", 0),
        ("https://stackoverflow.com", 0),
        ("https://www.amazon.com", 0),
        ("https://www.netflix.com", 0),
        ("http://free-prize-win.xyz/login", 1),
        ("http://update-apple-id.com/signin", 1),
        ("http://192.168.1.1/bank-login", 1),
        ("http://verify-account-secure.net", 1),
        ("http://crypto-airdrop-giveaway.xyz", 1),
    ]

    rows = []
    labels = []
    for url, label in data:
        feat = extract_features(url)
        rows.append(feat)
        labels.append(label)

    df = pd.DataFrame(rows)
    X = df[['url_len', 'domain_len', 'has_ip', 'has_https', 'dot_count', 'slash_count', 'keyword_count']]
    y = labels

    model = RandomForestClassifier(n_estimators=50, random_state=42)
    model.fit(X, y)

    joblib.dump((model, extract_features), 'phishing_model.joblib')
    print("Model trained and saved to phishing_model.joblib successfully!")

if __name__ == '__main__':
    train_model()
