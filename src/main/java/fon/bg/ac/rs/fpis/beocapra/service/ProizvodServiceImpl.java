package fon.bg.ac.rs.fpis.beocapra.service;

import fon.bg.ac.rs.fpis.beocapra.dto.ProizvodDTO;
import fon.bg.ac.rs.fpis.beocapra.model.ProizvodEntity;
import fon.bg.ac.rs.fpis.beocapra.repository.ProizvodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional @Slf4j
public class ProizvodServiceImpl implements ProizvodService {

    @Autowired
    private ProizvodRepository proizvodRepository;

    public Optional<ProizvodDTO> getById(Long id) {
        Optional<ProizvodEntity> proizvod = this.proizvodRepository.findById(id);
        if (!proizvod.isPresent()) return Optional.empty();
        return Optional.of(new ProizvodDTO(proizvod.get()));
    }

    public Collection<ProizvodDTO> getAll() {
        Collection<ProizvodEntity> proizvodi = this.proizvodRepository.findAll();
        return proizvodi
                        .stream()
                        .map(proizvodEntity -> new ProizvodDTO(proizvodEntity))
                        .collect(Collectors.toList());
    }

    public Optional<List<ProizvodDTO>> getByNameAndIdParts(String namePart, String idPart) {
        if (namePart == null || idPart == null) throw new RuntimeException("Name/id part can not be null");
        Collection<ProizvodEntity> proizvodi =
                    this.proizvodRepository.findByNazivProizvodaAndProizvodIdPart(namePart,idPart);
        if (proizvodi.isEmpty()) return Optional.empty();
        List<ProizvodDTO> dtos = proizvodi
                .stream()
                .map(entity -> new ProizvodDTO(entity))
                .collect(Collectors.toList());
        return Optional.of(dtos);
    }

    @Override
    public List<ProizvodDTO> getProductsWithSorting(String field) {
        List<ProizvodEntity> proizvodi = proizvodRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        return proizvodi
                .stream()
                .map(entity -> new ProizvodDTO(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProizvodEntity> getProductsWithPagination(int offset, int pageSize) {
        Page<ProizvodEntity> page = proizvodRepository.findAll(PageRequest.of(offset,pageSize));
        return page;
    }

    @Override
    public Page<ProizvodEntity> getProductsWithPaginationAndSorting(String field, int offset, int pageSize) {
        return proizvodRepository.findAll(PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.ASC,field)));
    }

}
