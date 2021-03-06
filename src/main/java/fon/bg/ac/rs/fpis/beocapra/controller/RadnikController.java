package fon.bg.ac.rs.fpis.beocapra.controller;

import fon.bg.ac.rs.fpis.beocapra.dto.RadnikDTO;
import fon.bg.ac.rs.fpis.beocapra.service.RadnikService;
import fon.bg.ac.rs.fpis.beocapra.service.RadnikServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/radnik")
@RequiredArgsConstructor
public class RadnikController {

    @Autowired
    private RadnikServiceImpl service;

    @GetMapping()
    public ResponseEntity<Collection<RadnikDTO>> getAll() {
        Collection<RadnikDTO> radnici = service.getAll();
        return ResponseEntity.ok().body(radnici);
    }

    @GetMapping("/{username}")
    public ResponseEntity<RadnikDTO> getByUsername(@PathVariable String username) {
        Optional<RadnikDTO> radnikDTO = service.getByUsername(username);
        if (!radnikDTO.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(radnikDTO.get());
    }

}
