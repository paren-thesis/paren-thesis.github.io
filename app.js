let terms = [];

// Fetch all terms initially and store them in memory for quick search
async function fetchTerms() {
  let letters = 'abcdefghijklmnopqrstuvwxyz'.split('');
  
  for (let letter of letters) {
    let response = await fetch(`terms/${letter}/terms.json`);
    if (response.ok) {
      let json = await response.json();
      terms = terms.concat(json);
    }
  }
  displayTerms(terms);  // Display all terms initially
}

// Search and filter terms dynamically
function searchTerms() {
  let input = document.getElementById('search').value.toLowerCase();
  let filteredTerms = terms.filter(term => term.term.toLowerCase().includes(input));
  displayTerms(filteredTerms);
}

// Display the filtered or full list of terms
function displayTerms(terms) {
  let list = document.getElementById('terms-list');
  list.innerHTML = '';  // Clear the current list

  terms.forEach(term => {
    let listItem = document.createElement('li');

    let link = document.createElement('a');
    link.href = `term.html?term=${term.term}`;  // Link to the term's definition page
    link.textContent = term.term;
    link.className = 'term-link';

    listItem.appendChild(link);
    list.appendChild(listItem);  // Add the term to the list
  });
}

// Load terms on page load
fetchTerms();
