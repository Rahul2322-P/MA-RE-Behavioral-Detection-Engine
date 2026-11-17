import React, { useState } from 'react';
import { uploadLogFile } from '../api/api';
import StatusBadge from '../components/StatusBadge';
import LoadingSpinner from '../components/LoadingSpinner';

export default function UploadLogs({ setActiveTab, onAnalysisComplete }) {
  const [selectedFile, setSelectedFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [result, setResult] = useState(null);

  function handleFileChange(e) {
    const file = e.target.files[0];
    if (file) {
      if (!file.name.toLowerCase().endsWith('.json')) {
        setError('Please select a valid JSON log file (.json)');
        setSelectedFile(null);
        return;
      }
      setError(null);
      setSelectedFile(file);
      setResult(null);
    }
  }

  async function handleUpload(e) {
    e.preventDefault();
    if (!selectedFile) {
      setError('Please select a JSON log file first.');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      const res = await uploadLogFile(selectedFile);
      setResult(res);
      if (onAnalysisComplete) {
        onAnalysisComplete();
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Upload Security Logs</h1>
        <p className="page-subtitle">Select and upload JSON process creation logs for rule-based behavioral detection.</p>
      </div>

      <div className="card" style={{ maxWidth: '700px', margin: '0 auto' }}>
        <form onSubmit={handleUpload}>
          <div style={{ border: '2px dashed var(--border-color)', borderRadius: '8px', padding: '2.5rem', textAlign: 'center', marginBottom: '1.5rem', background: '#131b2e' }}>
            <div style={{ fontSize: '2.5rem', marginBottom: '1rem' }}>📁</div>
            <h3 style={{ marginBottom: '0.5rem' }}>Select JSON Security Log File</h3>
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginBottom: '1.5rem' }}>
              Supported format: Standard JSON process event logs (Max file size: 10MB)
            </p>

            <input 
              type="file" 
              accept=".json"
              onChange={handleFileChange}
              id="file-input"
              style={{ display: 'none' }}
            />
            
            <label 
              htmlFor="file-input"
              style={{ 
                display: 'inline-block', 
                padding: '0.6rem 1.2rem', 
                background: 'var(--bg-hover)', 
                color: '#fff', 
                borderRadius: '6px', 
                cursor: 'pointer',
                border: '1px solid var(--border-color)',
                fontWeight: '500'
              }}
            >
              Browse Log File...
            </label>

            {selectedFile && (
              <div style={{ marginTop: '1rem', color: 'var(--accent-blue)', fontWeight: '600' }}>
                Selected: {selectedFile.name} ({(selectedFile.size / 1024).toFixed(1)} KB)
              </div>
            )}
          </div>

          {error && (
            <div style={{ padding: '0.8rem 1rem', background: 'rgba(239, 68, 68, 0.15)', border: '1px solid rgba(239, 68, 68, 0.4)', color: '#ef4444', borderRadius: '6px', marginBottom: '1.5rem' }}>
              ⚠️ {error}
            </div>
          )}

          {loading ? (
            <LoadingSpinner message="Evaluating log records against behavioral heuristics..." />
          ) : (
            <button 
              type="submit"
              disabled={!selectedFile}
              style={{ 
                width: '100%', 
                padding: '0.8rem', 
                background: selectedFile ? 'var(--accent-blue)' : 'var(--bg-hover)', 
                color: selectedFile ? '#0f172a' : 'var(--text-muted)', 
                border: 'none', 
                borderRadius: '6px', 
                fontSize: '1rem', 
                fontWeight: '700',
                cursor: selectedFile ? 'pointer' : 'not-allowed'
              }}
            >
              Analyze Security Logs
            </button>
          )}
        </form>
      </div>

      {/* Analysis Result Summary Box */}
      {result && (
        <div className="card" style={{ maxWidth: '700px', margin: '2rem auto 0 auto', borderColor: 'var(--accent-blue)' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1rem' }}>
            <span style={{ fontSize: '1.5rem' }}>✅</span>
            <h3 style={{ color: 'var(--accent-blue)' }}>Analysis Run #{result.analysisId} Completed</h3>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: '1rem', marginBottom: '1.5rem', background: '#131b2e', padding: '1rem', borderRadius: '6px' }}>
            <div>
              <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>File Name</div>
              <div style={{ fontWeight: '600' }}>{result.fileName}</div>
            </div>
            <div>
              <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>Events Processed</div>
              <div style={{ fontWeight: '600' }}>{result.totalEvents}</div>
            </div>
            <div>
              <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>Alerts Triggered</div>
              <div style={{ fontWeight: '700', color: result.alertCount > 0 ? 'var(--severity-high)' : 'var(--accent-blue)' }}>
                {result.alertCount}
              </div>
            </div>
          </div>

          {result.alertCount > 0 && (
            <div>
              <h4 style={{ marginBottom: '0.75rem' }}>Generated Detections ({result.alerts.length})</h4>
              <div className="table-container" style={{ marginBottom: '1.5rem' }}>
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>Rule ID</th>
                      <th>Rule Name</th>
                      <th>Severity</th>
                      <th>MITRE ID</th>
                    </tr>
                  </thead>
                  <tbody>
                    {result.alerts.map((al) => (
                      <tr key={al.id}>
                        <td><code>{al.ruleId}</code></td>
                        <td>{al.ruleName}</td>
                        <td><StatusBadge severity={al.severity} /></td>
                        <td><span style={{ color: 'var(--accent-blue)' }}>{al.mitreTechniqueId}</span></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          <div style={{ display: 'flex', gap: '1rem' }}>
            <button 
              onClick={() => setActiveTab('alerts')}
              style={{ flex: 1, padding: '0.6rem', background: 'var(--accent-blue)', color: '#0f172a', border: 'none', borderRadius: '6px', fontWeight: '600', cursor: 'pointer' }}
            >
              View Detailed Alerts &rarr;
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
