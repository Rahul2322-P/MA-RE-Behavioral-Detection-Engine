const API_BASE_URL = '/api';

export async function uploadLogFile(file) {
  const formData = new FormData();
  formData.append('file', file);

  const response = await fetch(`${API_BASE_URL}/analyses`, {
    method: 'POST',
    body: formData,
  });

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.message || `Upload failed with status code ${response.status}`);
  }

  return response.json();
}

export async function fetchDashboardSummary() {
  const response = await fetch(`${API_BASE_URL}/dashboard/summary`);
  if (!response.ok) {
    throw new Error(`Failed to load dashboard metrics (HTTP ${response.status})`);
  }
  return response.json();
}

export async function fetchAllAnalyses() {
  const response = await fetch(`${API_BASE_URL}/analyses`);
  if (!response.ok) {
    throw new Error(`Failed to load analysis history (HTTP ${response.status})`);
  }
  return response.json();
}

export async function fetchAnalysisById(id) {
  const response = await fetch(`${API_BASE_URL}/analyses/${id}`);
  if (!response.ok) {
    throw new Error(`Failed to load analysis run #${id}`);
  }
  return response.json();
}

export async function fetchAlerts(severity = '', search = '') {
  const params = new URLSearchParams();
  if (severity && severity !== 'ALL') {
    params.append('severity', severity);
  }
  if (search) {
    params.append('search', search);
  }

  const url = `${API_BASE_URL}/alerts${params.toString() ? '?' + params.toString() : ''}`;
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error(`Failed to load security alerts (HTTP ${response.status})`);
  }
  return response.json();
}

export async function updateAlertStatus(alertId, status) {
  const response = await fetch(`${API_BASE_URL}/alerts/${alertId}/status`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ status }),
  });

  if (!response.ok) {
    throw new Error(`Failed to update alert status (HTTP ${response.status})`);
  }

  return response.json();
}
