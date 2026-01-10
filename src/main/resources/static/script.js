const API_BASE = 'http://localhost:8080/api/jobs';

document.getElementById("submitButton").addEventListener("click", submitJob);

async function submitJob() {
    const statusDiv = document.getElementById("submitStatus");
    try {
          const type = document.getElementById('jobType').value;
          const payloadText = document.getElementById('payload').value;
          if (!payloadText) {
              statusDiv.textContent = "Error: Job payload cannot be empty";
              return;
          }
          const payload = JSON.parse(payloadText);
          const response = await fetch(API_BASE, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ type, payload })
          });

          if (!response.ok) {
              const error = await response.text();
              statusDiv.textContent = "Error: " + error;
              return;
          }

          const job = await response.json();
          statusDiv.textContent = `Job queued successfully | ID: ${job.jobId} | Status: ${job.status}`;
          addJobToTable(job);

    } catch (error) {
          statusDiv.textContent = "Error : Invalid payload JSON";
    }

}

function addJobToTable(job) {
    const tableBody = document.getElementById("jobTableBody");

    const row = document.createElement("tr");

    row.appendChild(createCell(job.jobId));
    row.appendChild(createCell(job.type));
    row.appendChild(createCell(job.status));
    row.appendChild(createCell(formatTime(job.createdAt)));
    row.appendChild(createCell(job.result || "-"));

    tableBody.appendChild(row);
}

function createCell(value) {
    const td = document.createElement("td");
    td.textContent = value;
    return td;
}

function formatTime(isoTime) {
    return new Date(isoTime).toLocaleString();
}

async function updateJobs() {
    try {
        const response = await fetch(API_BASE);
        const jobs = await response.json();
        const tbody = document.getElementById('jobTableBody');
        tbody.innerHTML = '';
        jobs.forEach(job => addJobToTable(job));

    } catch (error) {
        console.error('Error in fetching job updates:', error);
    }
}

setInterval(updateJobs, 5000);
updateJobs();
