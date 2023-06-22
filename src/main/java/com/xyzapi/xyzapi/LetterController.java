package com.xyzapi.xyzapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/letters")
public class LetterController {

    @GetMapping("/{letters}/{size}")
    public ResponseEntity<LetterGrid> generateLetterGrid(
            @PathVariable String letters,
            @PathVariable int size
    ) {
        validateInput(letters, size);
        String[] letterGrid = generateLetterGridArray(letters.charAt(0), size);
        LetterGrid result = new LetterGrid(letterGrid);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<LetterGrid[]> generateLetterGrids(
            @RequestBody LetterSpecification letterSpecification
    ) {
        validateInput(letterSpecification.getLetters(), letterSpecification.getSize());
        String letters = letterSpecification.getLetters();
        int size = letterSpecification.getSize();
        LetterGrid[] letterGrids = new LetterGrid[letters.length()];

        for (int i = 0; i < letters.length(); i++) {
            String[] letterGrid = generateLetterGridArray(letters.charAt(i), size);
            letterGrids[i] = new LetterGrid(letterGrid);
        }

        return ResponseEntity.ok(letterGrids);
    }

    private void validateInput(String letters, int size) {
        if (letters.isEmpty() || size <= 0 || size % 2 == 0) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    private String[] generateLetterGridArray(char letter, int size) {
        String[] letterGrid = new String[size];
        String line = "*".repeat(size);

        for (int i = 0; i < size; i++) {
            if (i == size / 2) {
                letterGrid[i] = line;
            } else {
                letterGrid[i] = line.replaceAll("[^\\s]", " ");
            }
        }

        return letterGrid;
    }
}
