const API_BASE = 'http://localhost:8080/api/jobs';

async function submitJob() {
      const type = document.getElementById('jobType').value;
      const payloadText = document.getElementById('payload').value;
      const payload = JSON.parse(payloadText);
      const response = await fetch(API_BASE, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ type, payload })
      });
console.log(response);
}