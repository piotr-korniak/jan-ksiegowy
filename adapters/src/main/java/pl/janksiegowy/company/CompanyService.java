package pl.janksiegowy.company;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository repository;

    public CompanyService( CompanyRepository repository) {
        this.repository= repository;
    }

    public Optional<CompanyJpa> findById( long id) {
        return repository.findById( id);
    }
}
