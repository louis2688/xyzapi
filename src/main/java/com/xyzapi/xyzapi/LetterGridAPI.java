package com.xyzapi.xyzapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LetterGridAPI {

    public static void main(String[] args) {
        SpringApplication.run(LetterGridAPI.class, args);
    }

    @RestController
    @RequestMapping("/letter-grid")
    public static class LetterGridController {

        @GetMapping("/{letters}/{size}")
        public ResponseEntity<List<LetterGridResponse>> generateLetterGrid(
                @PathVariable String letters,
                @PathVariable int size) {

            if (!isValidLetters(letters) || !isValidSize(size)) {
                return ResponseEntity.badRequest().build();
            }

            List<LetterGridResponse> letterGrids = new ArrayList<>();

            for (char letter : letters.toCharArray()) {
                letterGrids.add(generateGridForLetter(letter, size));
            }

            return ResponseEntity.ok(letterGrids);
        }

        private boolean isValidLetters(String letters) {
            return letters.matches("[XYZ]+");
        }

        private boolean isValidSize(int size) {
            return size > 0 && size % 2 == 1;
        }

        private LetterGridResponse generateGridForLetter(char letter, int size) {
            LetterGridResponse response = new LetterGridResponse();
            List<String> grid = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < size; j++) {
                    if (isLetterCharacter(i, j, letter, size)) {
                        row.append("*");
                    } else {
                        row.append(" ");
                    }
                }
                grid.add(row.toString());
            }

            response.setLetterGrid(grid);
            return response;
        }

        private boolean isLetterCharacter(int i, int j, char letter, int size) {
            switch (Character.toUpperCase(letter)) {
                case 'X':
                    return i == j || i == (size - 1 - j);
                case 'Y':
                    return (i == j && i <= size / 2) || (i >= size / 2 && j == (size / 2)) || (i == (size - 1 - j) && i <= size / 2);
                case 'Z':
                    return i == 0 || i == (size - 1) || i == (size - 1 - j);
                default:
                    return false;
            }
        }
    }

    public static class LetterGridResponse {
        private List<String> letterGrid;

        public List<String> getLetterGrid() {
            return letterGrid;
        }

        public void setLetterGrid(List<String> letterGrid) {
            this.letterGrid = letterGrid;
        }
    }
}
