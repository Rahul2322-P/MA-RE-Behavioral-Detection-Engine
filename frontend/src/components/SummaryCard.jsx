import React from 'react';

export default function SummaryCard({ title, value, color, icon }) {
  return (
    <div className="card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div className="card-title">{title}</div>
        {icon && <span style={{ fontSize: '1.2rem' }}>{icon}</span>}
      </div>
      <div className="card-value" style={{ color: color || 'var(--text-primary)' }}>
        {value}
      </div>
    </div>
  );
}
