import React, { useState, useEffect } from 'react';
import { fetchAllAnalyses, fetchAlerts } from '../api/api';
import LoadingSpinner from '../components/LoadingSpinner';
import StatusBadge from '../components/StatusBadge';

export default function DatabaseExplorer() {
  const [selectedTable, setSelectedTable] = useState('security_alerts');
  const [runs, setRuns] = useState([]);
  const [alerts, setAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  async function loadData() {
    try {
      setLoading(true);
      setError(null);
      const [runsData, alertsData] = await Promise.all([
        fetchAllAnalyses(),
        fetchAlerts()
      ]);
      setRuns(runsData);
      setAlerts(alertsData);
    } catch (err) {
      setError(err.message || 'Failed to query database records.');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadData();
  }, []);

  return (
    <div className="page-container">
      <div className="page-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <div>
          <h1 className="page-title">🗄️ MySQL Database Explorer</h1>
          <p className="page-subtitle">
            Direct real-time view of <code>lol_detector_db</code> stored on local MySQL Server (Port 3306)
          </p>
        </div>
        <button className="btn btn-secondary" onClick={loadData} title="Refresh records from MySQL">
          🔄 Refresh Tables
        </button>
      </div>

      {/* Database Connection Status Card */}
      <div className="card" style={{ marginBottom: '1.5rem', borderLeft: '4px solid #22c55e' }}>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '2rem', alignItems: 'center' }}>
          <div>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Status</span>
            <p style={{ fontWeight: 600, color: '#22c55e', display: 'flex', alignItems: 'center', gap: '0.5rem', marginTop: '0.2rem' }}>
              <span style={{ width: '10px', height: '10px', borderRadius: '50%', backgroundColor: '#22c55e', display: 'inline-block' }}></span>
              Connected to MySQL 8.0
            </p>
          </div>
          <div>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Host & Port</span>
            <p style={{ fontWeight: 600, color: 'var(--text-primary)', marginTop: '0.2rem', fontFamily: 'var(--font-mono)' }}>localhost:3306</p>
          </div>
          <div>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Database (Schema)</span>
            <p style={{ fontWeight: 600, color: 'var(--accent-blue)', marginTop: '0.2rem', fontFamily: 'var(--font-mono)' }}>lol_detector_db</p>
          </div>
          <div>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>User Account</span>
            <p style={{ fontWeight: 600, color: 'var(--text-primary)', marginTop: '0.2rem', fontFamily: 'var(--font-mono)' }}>root (Password: Rahul@2322)</p>
          </div>
          <div>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Total Rows</span>
            <p style={{ fontWeight: 600, color: 'var(--text-primary)', marginTop: '0.2rem' }}>{runs.length + alerts.length} Records</p>
          </div>
        </div>
      </div>

      {/* Table Selector Tabs */}
      <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
        <button
          className={`btn ${selectedTable === 'security_alerts' ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setSelectedTable('security_alerts')}
        >
          📄 Table: security_alerts ({alerts.length} rows)
        </button>
        <button
          className={`btn ${selectedTable === 'analysis_runs' ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setSelectedTable('analysis_runs')}
        >
          📄 Table: analysis_runs ({runs.length} rows)
        </button>
        <button
          className={`btn ${selectedTable === 'workbench_guide' ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setSelectedTable('workbench_guide')}
        >
          🔌 MySQL Workbench Setup Instructions
        </button>
      </div>

      {loading && <LoadingSpinner message="Querying MySQL database tables..." />}

      {error && (
        <div className="card error-banner">
          <p>⚠️ {error}</p>
        </div>
      )}

      {/* Table 1: security_alerts */}
      {!loading && !error && selectedTable === 'security_alerts' && (
        <div className="card">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 600 }}>
              Database Table: <code style={{ color: 'var(--accent-blue)' }}>security_alerts</code>
            </h3>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>
              Mapped to Entity: <code>SecurityAlert.java</code>
            </span>
          </div>

          <div className="table-responsive">
            <table className="data-table">
              <thead>
                <tr>
                  <th>id</th>
                  <th>analysis_id</th>
                  <th>rule_id</th>
                  <th>severity</th>
                  <th>mitre_technique_id</th>
                  <th>process_name</th>
                  <th>raw_command_line</th>
                  <th>user_account</th>
                  <th>investigation_status</th>
                </tr>
              </thead>
              <tbody>
                {alerts.length === 0 ? (
                  <tr>
                    <td colSpan="9" style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                      No alert rows found in MySQL. Upload a log file in the "Upload Logs" tab to generate alerts.
                    </td>
                  </tr>
                ) : (
                  alerts.map((a) => (
                    <tr key={a.id}>
                      <td style={{ fontFamily: 'var(--font-mono)', fontWeight: 600 }}>{a.id}</td>
                      <td style={{ fontFamily: 'var(--font-mono)' }}>{a.analysisId}</td>
                      <td><span className="badge badge-outline">{a.ruleId}</span></td>
                      <td><StatusBadge type="severity" value={a.severity} /></td>
                      <td style={{ fontFamily: 'var(--font-mono)', color: 'var(--accent-blue)' }}>{a.mitreTechniqueId}</td>
                      <td style={{ fontFamily: 'var(--font-mono)' }}>{a.processName}</td>
                      <td style={{ maxWidth: '350px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap', fontFamily: 'var(--font-mono)', fontSize: '0.85rem' }} title={a.rawCommandLine}>
                        {a.rawCommandLine}
                      </td>
                      <td style={{ fontSize: '0.85rem' }}>{a.userAccount || 'N/A'}</td>
                      <td><StatusBadge type="status" value={a.investigationStatus} /></td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Table 2: analysis_runs */}
      {!loading && !error && selectedTable === 'analysis_runs' && (
        <div className="card">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 600 }}>
              Database Table: <code style={{ color: 'var(--accent-blue)' }}>analysis_runs</code>
            </h3>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>
              Mapped to Entity: <code>AnalysisRun.java</code>
            </span>
          </div>

          <div className="table-responsive">
            <table className="data-table">
              <thead>
                <tr>
                  <th>id</th>
                  <th>file_name</th>
                  <th>started_at</th>
                  <th>completed_at</th>
                  <th>total_events</th>
                  <th>rejected_events</th>
                  <th>alert_count</th>
                  <th>status</th>
                </tr>
              </thead>
              <tbody>
                {runs.length === 0 ? (
                  <tr>
                    <td colSpan="8" style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                      No session rows found in MySQL.
                    </td>
                  </tr>
                ) : (
                  runs.map((r) => (
                    <tr key={r.id}>
                      <td style={{ fontFamily: 'var(--font-mono)', fontWeight: 600 }}>{r.id}</td>
                      <td style={{ fontWeight: 600 }}>{r.fileName}</td>
                      <td style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>{r.startedAt ? new Date(r.startedAt).toLocaleString() : 'N/A'}</td>
                      <td style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>{r.completedAt ? new Date(r.completedAt).toLocaleString() : 'N/A'}</td>
                      <td style={{ textAlign: 'center' }}>{r.totalEvents}</td>
                      <td style={{ textAlign: 'center' }}>{r.rejectedEvents}</td>
                      <td style={{ textAlign: 'center', fontWeight: 600, color: r.alertCount > 0 ? 'var(--severity-high)' : 'var(--text-muted)' }}>{r.alertCount}</td>
                      <td><span className="badge badge-success">{r.status}</span></td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Guide: How to connect in MySQL Workbench */}
      {selectedTable === 'workbench_guide' && (
        <div className="card">
          <h3 style={{ fontSize: '1.2rem', fontWeight: 600, marginBottom: '1rem' }}>
            🛠️ How to Connect MySQL Workbench to this Database
          </h3>
          <p style={{ color: 'var(--text-secondary)', marginBottom: '1.5rem' }}>
            <strong>Important:</strong> MySQL Workbench is a desktop client, not a web browser. It does <em>not</em> take a web URL (like <code>http://localhost:8085</code>). Instead, use the native database connection settings below:
          </p>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1rem', marginBottom: '1.5rem' }}>
            <div style={{ padding: '1rem', backgroundColor: 'var(--bg-primary)', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>1. Connection Name</span>
              <p style={{ fontWeight: 600, marginTop: '0.2rem' }}>LOLBin Detector DB</p>
            </div>
            <div style={{ padding: '1rem', backgroundColor: 'var(--bg-primary)', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>2. Hostname</span>
              <p style={{ fontWeight: 600, marginTop: '0.2rem', fontFamily: 'var(--font-mono)' }}>127.0.0.1 <span style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>(or localhost)</span></p>
            </div>
            <div style={{ padding: '1rem', backgroundColor: 'var(--bg-primary)', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>3. Port</span>
              <p style={{ fontWeight: 600, marginTop: '0.2rem', fontFamily: 'var(--font-mono)' }}>3306</p>
            </div>
            <div style={{ padding: '1rem', backgroundColor: 'var(--bg-primary)', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>4. Username</span>
              <p style={{ fontWeight: 600, marginTop: '0.2rem', fontFamily: 'var(--font-mono)' }}>root</p>
            </div>
            <div style={{ padding: '1rem', backgroundColor: 'var(--bg-primary)', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>5. Password</span>
              <p style={{ fontWeight: 600, marginTop: '0.2rem', fontFamily: 'var(--font-mono)', color: '#22c55e' }}>Rahul@2322</p>
            </div>
            <div style={{ padding: '1rem', backgroundColor: 'var(--bg-primary)', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>6. Default Schema</span>
              <p style={{ fontWeight: 600, marginTop: '0.2rem', fontFamily: 'var(--font-mono)', color: 'var(--accent-blue)' }}>lol_detector_db</p>
            </div>
          </div>

          <div style={{ backgroundColor: 'var(--bg-primary)', padding: '1.25rem', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
            <h4 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '0.75rem', color: 'var(--accent-blue)' }}>
              SQL Queries to Run in MySQL Workbench Query Editor:
            </h4>
            <pre style={{ backgroundColor: '#090d16', padding: '1rem', borderRadius: '6px', overflowX: 'auto', color: '#38bdf8', fontSize: '0.9rem', fontFamily: 'var(--font-mono)' }}>
{`USE lol_detector_db;

-- 1. View all log upload sessions:
SELECT * FROM analysis_runs;

-- 2. View all security alerts with MITRE techniques:
SELECT id, rule_id, severity, process_name, mitre_technique_id, investigation_status 
FROM security_alerts;`}
            </pre>
          </div>
        </div>
      )}
    </div>
  );
}
