import React, { useEffect, useState } from 'react';
import { fetchDashboardSummary } from '../api/api';
import SummaryCard from '../components/SummaryCard';
import StatusBadge from '../components/StatusBadge';
import LoadingSpinner from '../components/LoadingSpinner';

export default function Dashboard({ setActiveTab, onSelectAlert }) {
  const [summary, setSummary] = useState({
    totalAnalyses: 0,
    totalEventsProcessed: 0,
    totalAlertsGenerated: 0,
    alertsBySeverity: { CRITICAL: 0, HIGH: 0, MEDIUM: 0, LOW: 0 },
    recentAnalyses: [],
    recentAlerts: []
  });
  const [loading, setLoading] = useState(true);
  const [backendOffline, setBackendOffline] = useState(false);

  useEffect(() => {
    loadDashboard();
  }, []);

  async function loadDashboard() {
    try {
      setLoading(true);
      setBackendOffline(false);
      const data = await fetchDashboardSummary();
      setSummary(data);
    } catch (err) {
      console.warn('Backend API offline or unreachable:', err);
      setBackendOffline(true);
    } finally {
      setLoading(false);
    }
  }

  if (loading) return <LoadingSpinner message="Loading dashboard analytics..." />;

  const criticalCount = summary?.alertsBySeverity?.CRITICAL || 0;
  const highCount = summary?.alertsBySeverity?.HIGH || 0;
  const mediumCount = summary?.alertsBySeverity?.MEDIUM || 0;
  const lowCount = summary?.alertsBySeverity?.LOW || 0;

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Detection Overview</h1>
        <p className="page-subtitle">Real-time heuristics log analysis & MITRE ATT&CK mapping metrics.</p>
      </div>

      {backendOffline && (
        <div style={{ padding: '0.8rem 1.2rem', background: 'rgba(250, 204, 21, 0.15)', border: '1px solid rgba(250, 204, 21, 0.4)', color: '#facc15', borderRadius: '6px', marginBottom: '1.5rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            ⚠️ <strong>Backend Offline:</strong> Spring Boot is not connected on <code>http://localhost:8085</code>. Run <code>.\run_backend.ps1</code> in <code>backend/</code> to enable live REST APIs.
          </div>
          <button 
            onClick={loadDashboard}
            style={{ padding: '0.3rem 0.8rem', background: 'var(--bg-hover)', color: '#fff', border: '1px solid var(--border-color)', borderRadius: '4px', cursor: 'pointer' }}
          >
            Retry Connection
          </button>
        </div>
      )}

      {/* Summary Metrics */}
      <div className="metrics-grid">
        <SummaryCard title="Total Analyses" value={summary?.totalAnalyses || 0} icon="📊" />
        <SummaryCard title="Events Processed" value={summary?.totalEventsProcessed || 0} icon="📑" />
        <SummaryCard title="Alerts Generated" value={summary?.totalAlertsGenerated || 0} color="var(--accent-blue)" icon="🚨" />
        <SummaryCard title="Critical Detections" value={criticalCount} color="var(--severity-critical)" icon="⚡" />
      </div>

      {/* Severity Breakdown */}
      <div className="card" style={{ marginBottom: '2rem' }}>
        <h3 className="card-title" style={{ marginBottom: '1rem' }}>Alerts Severity Breakdown</h3>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(150px, 1fr))', gap: '1rem' }}>
          <div style={{ background: '#172033', padding: '1rem', borderRadius: '6px', borderLeft: '4px solid var(--severity-critical)' }}>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>CRITICAL</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--severity-critical)' }}>{criticalCount}</div>
          </div>
          <div style={{ background: '#172033', padding: '1rem', borderRadius: '6px', borderLeft: '4px solid var(--severity-high)' }}>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>HIGH</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--severity-high)' }}>{highCount}</div>
          </div>
          <div style={{ background: '#172033', padding: '1rem', borderRadius: '6px', borderLeft: '4px solid var(--severity-medium)' }}>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>MEDIUM</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--severity-medium)' }}>{mediumCount}</div>
          </div>
          <div style={{ background: '#172033', padding: '1rem', borderRadius: '6px', borderLeft: '4px solid var(--severity-low)' }}>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>LOW</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--severity-low)' }}>{lowCount}</div>
          </div>
        </div>
      </div>

      {/* Recent Alerts */}
      <div className="card">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
          <h3>Recent Security Detections</h3>
          <button 
            onClick={() => setActiveTab('alerts')}
            style={{ background: 'none', border: 'none', color: 'var(--accent-blue)', cursor: 'pointer', fontWeight: '500' }}
          >
            View All Alerts &rarr;
          </button>
        </div>

        {summary?.recentAlerts?.length === 0 ? (
          <p style={{ color: 'var(--text-secondary)', padding: '1rem 0' }}>No security alerts recorded yet. Upload log files to analyze activity.</p>
        ) : (
          <div className="table-container">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Rule Name</th>
                  <th>Process Name</th>
                  <th>Severity</th>
                  <th>MITRE ATT&CK</th>
                  <th>Detected At</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {summary?.recentAlerts?.map((alert) => (
                  <tr key={alert.id}>
                    <td style={{ fontWeight: '600' }}>{alert.ruleName}</td>
                    <td><code style={{ fontFamily: 'var(--font-mono)', color: 'var(--accent-blue)' }}>{alert.processName}</code></td>
                    <td><StatusBadge severity={alert.severity} /></td>
                    <td>
                      <span style={{ fontSize: '0.85rem', color: '#38bdf8' }}>{alert.mitreTechniqueId}</span>
                    </td>
                    <td>{new Date(alert.detectedAt).toLocaleString()}</td>
                    <td>
                      <button
                        onClick={() => {
                          onSelectAlert(alert);
                          setActiveTab('alerts');
                        }}
                        style={{ padding: '0.3rem 0.6rem', background: 'var(--bg-hover)', color: '#fff', border: '1px solid var(--border-color)', borderRadius: '4px', cursor: 'pointer', fontSize: '0.8rem' }}
                      >
                        Details
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
