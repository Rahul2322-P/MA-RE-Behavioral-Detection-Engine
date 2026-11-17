import React, { useEffect, useState } from 'react';
import { fetchAllAnalyses } from '../api/api';
import LoadingSpinner from '../components/LoadingSpinner';

export default function AnalysisHistory() {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadHistory();
  }, []);

  async function loadHistory() {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchAllAnalyses();
      setHistory(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Analysis History</h1>
        <p className="page-subtitle">Persistent records of historical security log upload sessions stored in MySQL.</p>
      </div>

      {loading ? (
        <LoadingSpinner message="Fetching analysis session history..." />
      ) : error ? (
        <div className="card" style={{ borderColor: 'var(--severity-critical)', color: 'var(--severity-critical)' }}>
          <h3>Error Loading History</h3>
          <p>{error}</p>
        </div>
      ) : history.length === 0 ? (
        <div className="card" style={{ textAlign: 'center', padding: '3rem 1rem', color: 'var(--text-secondary)' }}>
          <div style={{ fontSize: '2.5rem', marginBottom: '1rem' }}>📜</div>
          <h3>No Previous Analysis Runs</h3>
          <p>No log file upload sessions recorded yet.</p>
        </div>
      ) : (
        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>Session ID</th>
                <th>File Name</th>
                <th>Started Timestamp</th>
                <th>Completed Timestamp</th>
                <th>Events Processed</th>
                <th>Alerts Generated</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {history.map((run) => (
                <tr key={run.id}>
                  <td style={{ fontWeight: '600' }}>#{run.id}</td>
                  <td style={{ color: 'var(--accent-blue)', fontWeight: '500' }}>{run.fileName}</td>
                  <td>{new Date(run.startedAt).toLocaleString()}</td>
                  <td>{run.completedAt ? new Date(run.completedAt).toLocaleString() : 'N/A'}</td>
                  <td>{run.totalEvents}</td>
                  <td>
                    <span style={{ fontWeight: '700', color: run.alertCount > 0 ? 'var(--severity-high)' : 'var(--text-primary)' }}>
                      {run.alertCount}
                    </span>
                  </td>
                  <td>
                    <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.15)', color: '#22c55e', border: '1px solid rgba(34, 197, 94, 0.3)' }}>
                      {run.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
