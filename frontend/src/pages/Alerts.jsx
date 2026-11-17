import React, { useEffect, useState } from 'react';
import { fetchAlerts } from '../api/api';
import StatusBadge from '../components/StatusBadge';
import LoadingSpinner from '../components/LoadingSpinner';
import AlertDetailsModal from './AlertDetailsModal';

export default function Alerts({ initialSelectedAlert }) {
  const [alerts, setAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const [severityFilter, setSeverityFilter] = useState('ALL');
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedAlert, setSelectedAlert] = useState(initialSelectedAlert || null);

  useEffect(() => {
    loadAlerts();
  }, [severityFilter, searchQuery]);

  useEffect(() => {
    if (initialSelectedAlert) {
      setSelectedAlert(initialSelectedAlert);
    }
  }, [initialSelectedAlert]);

  async function loadAlerts() {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchAlerts(severityFilter, searchQuery);
      setAlerts(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Security Alerts</h1>
        <p className="page-subtitle">Inspect behavioral detections, target processes, and MITRE ATT&CK mapping.</p>
      </div>

      {/* Filter and Search Bar */}
      <div className="card" style={{ marginBottom: '1.5rem' }}>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem', alignItems: 'center', justifyContent: 'space-between' }}>
          
          {/* Search Input */}
          <div style={{ flex: 1, minWidth: '250px' }}>
            <input 
              type="text"
              placeholder="Search by rule name, description, or process..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              style={{
                width: '100%',
                padding: '0.6rem 1rem',
                background: 'var(--bg-primary)',
                border: '1px solid var(--border-color)',
                borderRadius: '6px',
                color: 'var(--text-primary)',
                fontSize: '0.9rem'
              }}
            />
          </div>

          {/* Severity Dropdown */}
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Severity:</span>
            <select
              value={severityFilter}
              onChange={(e) => setSeverityFilter(e.target.value)}
              style={{
                padding: '0.6rem 1rem',
                background: 'var(--bg-primary)',
                border: '1px solid var(--border-color)',
                borderRadius: '6px',
                color: 'var(--text-primary)',
                fontSize: '0.9rem',
                fontWeight: '600'
              }}
            >
              <option value="ALL">All Severities</option>
              <option value="CRITICAL">CRITICAL</option>
              <option value="HIGH">HIGH</option>
              <option value="MEDIUM">MEDIUM</option>
              <option value="LOW">LOW</option>
            </select>
          </div>
        </div>
      </div>

      {/* Alerts Table */}
      {loading ? (
        <LoadingSpinner message="Fetching security alerts..." />
      ) : error ? (
        <div className="card" style={{ borderColor: 'var(--severity-critical)', color: 'var(--severity-critical)' }}>
          <h3>Error Loading Alerts</h3>
          <p>{error}</p>
        </div>
      ) : alerts.length === 0 ? (
        <div className="card" style={{ textAlign: 'center', padding: '3rem 1rem', color: 'var(--text-secondary)' }}>
          <div style={{ fontSize: '2.5rem', marginBottom: '1rem' }}>🔍</div>
          <h3>No Security Alerts Found</h3>
          <p>No alerts matched your active search criteria or no logs have been analyzed yet.</p>
        </div>
      ) : (
        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>Rule Name</th>
                <th>Process</th>
                <th>Severity</th>
                <th>MITRE ATT&CK</th>
                <th>Detected At</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {alerts.map((alert) => (
                <tr key={alert.id}>
                  <td style={{ fontWeight: '600' }}>
                    <div>{alert.ruleName}</div>
                    <div style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', fontFamily: 'var(--font-mono)' }}>{alert.ruleId}</div>
                  </td>
                  <td>
                    <code style={{ fontFamily: 'var(--font-mono)', color: 'var(--accent-blue)' }}>{alert.processName}</code>
                  </td>
                  <td><StatusBadge severity={alert.severity} /></td>
                  <td>
                    <span style={{ fontSize: '0.85rem', color: '#38bdf8', fontWeight: '600' }}>{alert.mitreTechniqueId}</span>
                  </td>
                  <td>{new Date(alert.detectedAt).toLocaleString()}</td>
                  <td><StatusBadge status={alert.investigationStatus} /></td>
                  <td>
                    <button
                      onClick={() => setSelectedAlert(alert)}
                      style={{ padding: '0.4rem 0.8rem', background: 'var(--bg-hover)', color: '#fff', border: '1px solid var(--border-color)', borderRadius: '4px', cursor: 'pointer', fontSize: '0.8rem', fontWeight: '500' }}
                    >
                      Inspect
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Detail Modal */}
      {selectedAlert && (
        <AlertDetailsModal 
          alert={selectedAlert}
          onClose={() => setSelectedAlert(null)}
          onStatusUpdated={loadAlerts}
        />
      )}
    </div>
  );
}
