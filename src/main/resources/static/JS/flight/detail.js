fetch('/detail', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams({
        itineraryId: 'example-itinerary-id',
        token: 'example-token'
    })
})
    .then(response => response.text())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));