import React, { useState } from 'react';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import UploadLogs from './pages/UploadLogs';
import Alerts from './pages/Alerts';
import AnalysisHistory from './pages/AnalysisHistory';
import DatabaseExplorer from './pages/DatabaseExplorer';

export default function App() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [selectedAlertForInspection, setSelectedAlertForInspection] = useState(null);

  function handleSelectAlert(alert) {
    setSelectedAlertForInspection(alert);
    setActiveTab('alerts');
  }

  function handleAnalysisComplete() {
    // Called when a log file is successfully uploaded and analyzed
  }

  return (
    <div className="app-container">
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} />

      <main className="main-content">
        {activeTab === 'dashboard' && (
          <Dashboard 
            setActiveTab={setActiveTab} 
            onSelectAlert={handleSelectAlert} 
          />
        )}

        {activeTab === 'upload' && (
          <UploadLogs 
            setActiveTab={setActiveTab} 
            onAnalysisComplete={handleAnalysisComplete} 
          />
        )}

        {activeTab === 'alerts' && (
          <Alerts 
            initialSelectedAlert={selectedAlertForInspection} 
          />
        )}

        {activeTab === 'history' && (
          <AnalysisHistory />
        )}

        {activeTab === 'database' && (
          <DatabaseExplorer />
        )}
      </main>

      <footer className="footer">
        <p>Behavioral Detection Engine for LOLBin Attacks &copy; 2026 | B.Tech Cybersecurity Capstone Project</p>
      </footer>
    </div>
  );
}
