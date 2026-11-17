import React from 'react';

export default function Navbar({ activeTab, setActiveTab }) {
  return (
    <header className="navbar">
      <div className="brand">
        <span>🛡️ LOLBin Behavioral Engine</span>
        <span className="brand-badge">B.Tech Capstone</span>
      </div>
      <nav>
        <ul className="nav-links">
          <li>
            <button 
              className={`nav-link ${activeTab === 'dashboard' ? 'active' : ''}`}
              onClick={() => setActiveTab('dashboard')}
            >
              Dashboard
            </button>
          </li>
          <li>
            <button 
              className={`nav-link ${activeTab === 'upload' ? 'active' : ''}`}
              onClick={() => setActiveTab('upload')}
            >
              Upload Logs
            </button>
          </li>
          <li>
            <button 
              className={`nav-link ${activeTab === 'alerts' ? 'active' : ''}`}
              onClick={() => setActiveTab('alerts')}
            >
              Security Alerts
            </button>
          </li>
          <li>
            <button 
              className={`nav-link ${activeTab === 'history' ? 'active' : ''}`}
              onClick={() => setActiveTab('history')}
            >
              Analysis History
            </button>
          </li>
          <li>
            <button 
              className={`nav-link ${activeTab === 'database' ? 'active' : ''}`}
              onClick={() => setActiveTab('database')}
              style={{ color: 'var(--accent-blue)', borderColor: 'rgba(56, 189, 248, 0.4)' }}
            >
              🗄️ Database Tables
            </button>
          </li>
        </ul>
      </nav>
    </header>
  );
}
