package pl.kostrowski.locking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kostrowski.locking.model.Person;
import pl.kostrowski.locking.service.TechnicalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    private final TechnicalService technicalService;

    @GetMapping(path = "/technical/getAll")
    public List<Person> getAllData() {
        return technicalService.getAll();
    }

}
