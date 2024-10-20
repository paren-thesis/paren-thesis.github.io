
# Nersh

Tech Terms Search is a simple web project hosted on GitHub Pages that allows users to search for technology-related terms and view their definitions. The project uses Markdown files to store each term's definition, and the terms are organized in a directory structure based on the first letter of each term. Users can navigate between terms using "next" and "previous" buttons on the definition pages.

The project features:
- A search page that looks similar to Google's search interface.
- Separate pages for each term's definition.
- Terms are stored in separate Markdown files, categorized alphabetically in folders.
- Navigation between terms on their individual pages.
- A JSON file for each alphabetic category to quickly list the available terms.

## Project Structure

```
tech-terms-search/
├── index.html               # Main search page (Google-like interface)
├── term.html                # Template for displaying term definitions
├── style.css                # Styling for the website (includes gradient and layout)
├── app.js                   # JavaScript for handling search functionality
├── terms/                   # Folder containing term categories
│   ├── a/                   # Folder for terms starting with "A"
│   │   ├── api.md           # Markdown file for the term "API"
│   │   └── terms.json       # JSON file listing all terms starting with "A"
│   ├── b/                   # Folder for terms starting with "B"
│   │   ├── blog.md          # Markdown file for the term "Blog"
│   │   └── terms.json       # JSON file listing all terms starting with "B"
│   └── ...                  # Additional alphabetic folders for terms
├── src/                     # Source folder 
├── AddTerm.java         # Java program for automating term addition
└── README.md                # This file
```

## Adding a New Term

You can add a new term to the project using a Java program. The program creates a Markdown file for the new term, places it in the appropriate folder based on the first letter, and updates the corresponding `terms.json` file for that folder.

### How to Use the Java Program:

1. **Set Up Your Environment**  
   Make sure you have Java installed on your machine. You can download it from [here](https://www.oracle.com/java/technologies/javase-downloads.html) if you haven't already.

2. **Add the Term**  
   To add a new term, open your terminal in the project root directory and run the Java program as follows:

   ```bash
   java src/AddTerm "TERM_NAME" "TERM_DESCRIPTION"
   ```

   - **TERM_NAME**: The name of the term you want to add (e.g., "CSS").
   - **TERM_DESCRIPTION**: The description of the term (e.g., "CSS is used to style HTML documents.").

3. **Example Command**  
   To add the term **CSS** with a description, you would run:

   ```bash
   java src/AddTerm "CSS" "CSS is used to style HTML documents."
   ```

4. **What Happens**  
   - A new Markdown file for the term is created in the appropriate folder (e.g., `terms/c/css.md`).
   - The `terms.json` file in the corresponding folder is updated to include the new term.
  
  ### AddTerm.java Source Code
  ```
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AddTerm {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java AddTerm <TERM_NAME> <TERM_DESCRIPTION>");
            return;
        }

        String termName = args[0];
        String termDescription = args[1];
        String firstLetter = termName.substring(0, 1).toLowerCase();
        String termsDirectory = "terms/" + firstLetter + "/";
        String markdownFilePath = termsDirectory + termName.toLowerCase() + ".md";
        String jsonFilePath = termsDirectory + "terms.json";

        try {
            // Create terms directory if it doesn't exist
            Files.createDirectories(Paths.get(termsDirectory));

            // Create Markdown file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(markdownFilePath))) {
                writer.write("# " + termName + "\\n\\n" + termDescription);
            }

            // Update JSON file
            List<String> termsList = new ArrayList<>();
            if (new File(jsonFilePath).exists()) {
                // Read existing terms
                try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        termsList.add(line);
                    }
                }
            }

            // Add new term if not already present
            if (!termsList.contains(termName)) {
                termsList.add(termName);

                // Write updated terms to JSON file
                try (BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(jsonFilePath))) {
                    jsonWriter.write("[\\n");
                    for (int i = 0; i < termsList.size(); i++) {
                        jsonWriter.write("  \"" + termsList.get(i) + "\"");
                        if (i < termsList.size() - 1) jsonWriter.write(",");
                        jsonWriter.write("\\n");
                    }
                    jsonWriter.write("]");
                }
            }

            System.out.println("Term added successfully!");

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
```

## Technologies Used

- **HTML**: For structuring the pages.
- **CSS**: For styling the search page and term pages, including a gradient design.
- **JavaScript**: For handling the search functionality and filtering terms.
- **Markdown**: Each term's definition is stored in a `.md` file for easy editing and formatting.
- **JSON**: To keep track of the terms available in each category.
- **Java**: To automate adding new terms to the project.

## How the Search Works

The search functionality allows users to type in a tech term, and it filters the available terms based on their input. When a user selects a term, they are redirected to the term's individual page where they can see the definition. Users can also navigate to the next and previous terms using arrow buttons.

## Hosting

This project is hosted on GitHub Pages. All files, including HTML, CSS, and JavaScript, are stored in the repository. You can access the live site at: [Nersh](https://paren-thesis.github.io).

## Contributing

If you'd like to contribute to this project by adding new terms or improving functionality:
1. Fork the repository.
2. Add your terms using the structure explained above.
3. Submit a pull request.

## License

This project is open source under the MIT License. Feel free to use, modify, and share!
