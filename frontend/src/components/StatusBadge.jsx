import React from 'react';

export default function StatusBadge({ severity, status }) {
  if (severity) {
    const sev = severity.toUpperCase();
    let badgeClass = 'badge-low';
    if (sev === 'CRITICAL') badgeClass = 'badge-critical';
    else if (sev === 'HIGH') badgeClass = 'badge-high';
    else if (sev === 'MEDIUM') badgeClass = 'badge-medium';

    return <span className={`badge ${badgeClass}`}>{sev}</span>;
  }

  if (status) {
    const stat = status.toUpperCase();
    let style = { backgroundColor: 'rgba(148, 163, 184, 0.15)', color: '#94a3b8', border: '1px solid rgba(148, 163, 184, 0.3)' };
    if (stat === 'NEW') {
      style = { backgroundColor: 'rgba(56, 189, 248, 0.15)', color: '#38bdf8', border: '1px solid rgba(56, 189, 248, 0.3)' };
    } else if (stat === 'INVESTIGATING') {
      style = { backgroundColor: 'rgba(250, 204, 21, 0.15)', color: '#facc15', border: '1px solid rgba(250, 204, 21, 0.3)' };
    } else if (stat === 'RESOLVED') {
      style = { backgroundColor: 'rgba(34, 197, 94, 0.15)', color: '#22c55e', border: '1px solid rgba(34, 197, 94, 0.3)' };
    } else if (stat === 'FALSE_POSITIVE') {
      style = { backgroundColor: 'rgba(148, 163, 184, 0.15)', color: '#94a3b8', border: '1px solid rgba(148, 163, 184, 0.3)' };
    }

    return (
      <span className="badge" style={style}>
        {stat}
      </span>
    );
  }

  return null;
}
