async function fetchTerms(letter) {
  try {
    let response = await fetch(`data/${letter.toLowerCase()}.json`); // Fetch from 'data' folder
    if (!response.ok) {
      throw new Error(`No terms found for the letter: ${letter}`);
    }
    let terms = await response.json();
    displayTerms(terms); // Display the terms and images
  } catch (error) {
    document.getElementById(
      "terms-list"
    ).innerHTML = `<li>${error.message}</li>`;
  }
}

function displayTerms(terms) {
  let list = document.getElementById("terms-list");
  list.innerHTML = ""; // Clear the list before adding new items

  terms.forEach((term) => {
    let listItem = document.createElement("li");

    let img = document.createElement("img");
    img.src = term.image; // Load the image from the external URL
    img.alt = term.term; // Set alt text for accessibility
    img.className = "term-image"; // Add a class for styling if needed

    let span = document.createElement("span");
    span.textContent = `${term.term}: ${term.definition}`;

    listItem.appendChild(img);
    listItem.appendChild(span);
    list.appendChild(listItem); // Add the item to the list
  });
}

function searchTerms() {
  let input = document.getElementById("search").value.trim();
  let firstLetter = input[0]; // Get the first letter of the search term
  if (firstLetter) {
    fetchTerms(firstLetter); // Fetch terms starting with the first letter
  } else {
    document.getElementById("terms-list").innerHTML = ""; // Clear if no input
  }
}
