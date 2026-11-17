import React, { useState } from 'react';
import StatusBadge from '../components/StatusBadge';
import { updateAlertStatus } from '../api/api';

export default function AlertDetailsModal({ alert, onClose, onStatusUpdated }) {
  const [currentStatus, setCurrentStatus] = useState(alert?.investigationStatus || 'NEW');
  const [updating, setUpdating] = useState(false);

  if (!alert) return null;

  async function handleStatusChange(e) {
    const newStatus = e.target.value;
    try {
      setUpdating(true);
      await updateAlertStatus(alert.id, newStatus);
      setCurrentStatus(newStatus);
      if (onStatusUpdated) onStatusUpdated();
    } catch (err) {
      console.error('Failed to update alert status:', err);
    } finally {
      setUpdating(false);
    }
  }

  return (
    <div style={{
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(15, 23, 42, 0.85)',
      backdropFilter: 'blur(4px)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      zIndex: 1000,
      padding: '1rem'
    }}>
      <div className="card" style={{ maxWidth: '800px', width: '100%', maxHeight: '90vh', overflowY: 'auto', border: '1px solid var(--border-color)', boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.5)' }}>
        
        {/* Header */}
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1.5rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '1rem' }}>
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '0.4rem' }}>
              <StatusBadge severity={alert.severity} />
              <span style={{ fontFamily: 'var(--font-mono)', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>{alert.ruleId}</span>
            </div>
            <h2 style={{ fontSize: '1.35rem', fontWeight: '700' }}>{alert.ruleName}</h2>
          </div>
          <button 
            onClick={onClose}
            style={{ background: 'none', border: 'none', color: 'var(--text-secondary)', fontSize: '1.5rem', cursor: 'pointer', padding: '0.2rem' }}
          >
            ✕
          </button>
        </div>

        {/* Metadata Grid */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1rem', marginBottom: '1.5rem', background: '#131b2e', padding: '1rem', borderRadius: '6px' }}>
          <div>
            <div style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', textTransform: 'uppercase' }}>Target Process</div>
            <div style={{ fontWeight: '600', fontFamily: 'var(--font-mono)', color: 'var(--accent-blue)' }}>{alert.processName}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', textTransform: 'uppercase' }}>Parent Process</div>
            <div style={{ fontWeight: '600', fontFamily: 'var(--font-mono)' }}>{alert.parentProcessName || 'N/A'}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', textTransform: 'uppercase' }}>User Account</div>
            <div style={{ fontWeight: '600' }}>{alert.userAccount || 'SYSTEM'}</div>
          </div>
          <div>
            <div style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', textTransform: 'uppercase' }}>Detected At</div>
            <div style={{ fontWeight: '600' }}>{new Date(alert.detectedAt).toLocaleString()}</div>
          </div>
        </div>

        {/* MITRE ATT&CK Mapping */}
        <div style={{ marginBottom: '1.5rem', padding: '1rem', background: 'rgba(56, 189, 248, 0.08)', border: '1px solid rgba(56, 189, 248, 0.2)', borderRadius: '6px' }}>
          <div style={{ fontSize: '0.75rem', color: 'var(--accent-blue)', fontWeight: '700', textTransform: 'uppercase', marginBottom: '0.25rem' }}>
            MITRE ATT&CK Enterprise Technique
          </div>
          <div style={{ fontSize: '1rem', fontWeight: '700' }}>
            {alert.mitreTechniqueId} — {alert.mitreTechniqueName}
          </div>
        </div>

        {/* Raw Command Line */}
        <div style={{ marginBottom: '1.5rem' }}>
          <h4 style={{ marginBottom: '0.5rem', color: 'var(--text-secondary)', fontSize: '0.85rem', textTransform: 'uppercase' }}>Raw Command Line</h4>
          <div className="cmd-box">{alert.rawCommandLine}</div>
        </div>

        {/* Behavioral Explanation */}
        <div style={{ marginBottom: '1.5rem' }}>
          <h4 style={{ marginBottom: '0.5rem', color: 'var(--text-secondary)', fontSize: '0.85rem', textTransform: 'uppercase' }}>Detection Heuristics Explanation</h4>
          <div style={{ color: 'var(--text-primary)', lineHeight: '1.6', background: 'var(--bg-primary)', padding: '1rem', borderRadius: '6px', border: '1px solid var(--border-color)' }}>
            {alert.description}
          </div>
        </div>

        {/* Investigation Status Control */}
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', paddingTop: '1rem', borderTop: '1px solid var(--border-color)' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>Investigation Status:</span>
            <select 
              value={currentStatus}
              onChange={handleStatusChange}
              disabled={updating}
              style={{
                padding: '0.4rem 0.8rem',
                background: 'var(--bg-primary)',
                color: 'var(--text-primary)',
                border: '1px solid var(--border-color)',
                borderRadius: '4px',
                fontWeight: '600'
              }}
            >
              <option value="NEW">NEW</option>
              <option value="INVESTIGATING">INVESTIGATING</option>
              <option value="RESOLVED">RESOLVED</option>
              <option value="FALSE_POSITIVE">FALSE POSITIVE</option>
            </select>
          </div>

          <button 
            onClick={onClose}
            style={{ padding: '0.5rem 1.2rem', background: 'var(--bg-hover)', color: '#fff', border: '1px solid var(--border-color)', borderRadius: '6px', cursor: 'pointer', fontWeight: '500' }}
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
