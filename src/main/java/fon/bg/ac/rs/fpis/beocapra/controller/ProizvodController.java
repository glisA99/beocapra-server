package fon.bg.ac.rs.fpis.beocapra.controller;

import fon.bg.ac.rs.fpis.beocapra.dto.ProizvodDTO;
import fon.bg.ac.rs.fpis.beocapra.exceptions.ProizvodNotFoundException;
import fon.bg.ac.rs.fpis.beocapra.service.ProizvodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proizvod")
@RequiredArgsConstructor
public class ProizvodController {

    @Autowired
    private ProizvodServiceImpl service;

    @GetMapping("/{id}")
    public ResponseEntity<ProizvodDTO> getProizvodById(@PathVariable long id) {
        Optional<ProizvodDTO> proizvodDTO = this.service.getById(id);
        if (!proizvodDTO.isPresent()) throw new ProizvodNotFoundException();
        return ResponseEntity.ok().body(proizvodDTO.get());
    }

    // agregate root
    @GetMapping()
    public ResponseEntity<Collection<ProizvodDTO>> getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<ProizvodDTO>> getAllByNameAndIdPart(
            @RequestParam(value = "name", required = false) String namePart,
            @RequestParam(value = "id", required = false) String idPart) {
        if (!StringUtils.hasText(namePart) && !StringUtils.hasText(idPart)) {
            return this.getAll();
        }
        Optional<List<ProizvodDTO>> proizvodi = this.service.getByNameAndIdParts(namePart,idPart);
        if (!proizvodi.isPresent()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(proizvodi.get());
    }

    @ControllerAdvice
    public static class ProizvodAdvice {

        @ResponseBody
        @ExceptionHandler(ProizvodNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String proizvodNotFoundHandler(ProizvodNotFoundException ex) {
            return ex.getMessage();
        }

    }
}
