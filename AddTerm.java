import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AddTerm {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java AddTerm \"TERM_NAME\" \"TERM_DESCRIPTION>\"");
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
            try (BufferedWriter markdownWriter = new BufferedWriter(new FileWriter(markdownFilePath))) {
                String markdownContent = "# " + termName + "\n\n" + termDescription;
                markdownWriter.write(markdownContent);
            }

            // Initialize termsList
            List<String> termsList = new ArrayList<>();

            // Read existing terms if jsonFilePath exists
            if (new File(jsonFilePath).exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
                    StringBuilder jsonContent = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonContent.append(line);
                    }

                    // Check if jsonContent is not empty before processing
                    if (jsonContent.length() > 0) {
                        String jsonArray = jsonContent.toString();
                        jsonArray = jsonArray.substring(1, jsonArray.length() - 1); // Remove the brackets
                        String[] termsArray = jsonArray.split("},"); // Split by each JSON object
                        for (String term : termsArray) {
                            // Extract the term using substring and regex
                            String cleanedTerm = term.replaceAll("[{}\"]", "").trim(); // Remove special characters
                            cleanedTerm = cleanedTerm.split(":")[1].trim(); // Get the actual term
                            termsList.add(cleanedTerm);
                        }
                    }
                }
            }

            // Add new term if not already present
            if (!termsList.contains(termName)) {
                termsList.add(termName);

                // Write updated terms to JSON file
                try (BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(jsonFilePath))) {
                    jsonWriter.write("[\n");
                    for (int i = 0; i < termsList.size(); i++) {
                        jsonWriter.write("  { \"term\": \"" + termsList.get(i) + "\" }");
                        if (i < termsList.size() - 1) jsonWriter.write(",\n"); // Add comma if not the last term
                    }
                    jsonWriter.write("\n]"); // Close the JSON array
                }
            }

            System.out.println("Term added successfully!\nMove to the directory and edit the new term");

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
