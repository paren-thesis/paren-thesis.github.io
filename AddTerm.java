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