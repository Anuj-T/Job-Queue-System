const API_BASE = 'http://localhost:8080/api/jobs';

document.getElementById("submitButton").addEventListener("click", submitJob);

async function submitJob() {
      const type = document.getElementById('jobType').value;
      const payloadText = document.getElementById('payload').value;
      const payload = JSON.parse(payloadText);
      const response = await fetch(API_BASE, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ type, payload })
      });
      const job = await response.json();
      console.log("reached here");
      console.log(job);
      addJobToTable(job);
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