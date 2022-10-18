package pl.kostrowski.locking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kostrowski.locking.dto.PersonDto;
import pl.kostrowski.locking.service.PersonService;
import pl.kostrowski.locking.util.LogUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    @GetMapping(path = "/all/nonplus/people")
    public List<PersonDto> getAllWithoutNPlusOne(@RequestParam("pageNo") Integer pageNo,
                                                 @RequestParam("pageSize") Integer pageSize) {
        List<PersonDto> all = personService.getAllWithoutNPlusOne(PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "bid"));
        log.debug(LogUtil.makeList(all, 0, true));
        return all;
    }

    @GetMapping(path = "/all/nplus/people")
    public List<PersonDto> getAllWittNPlusOne(@RequestParam("pageNo") Integer pageNo,
                                              @RequestParam("pageSize") Integer pageSize) {
        List<PersonDto> all = personService.getAllNPlusOne(PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "bid"));
        log.debug(LogUtil.makeList(all, 0, true));
        return all;
    }

    @GetMapping(path = "/all/people")
    public List<PersonDto> getAll() {
        List<PersonDto> all = personService.getAll();
        log.debug(LogUtil.makeList(all, 0, true));
        return all;
    }
    @GetMapping(path = "/all/people/set")
    public List<PersonDto> getAllInSet() {
        List<PersonDto> all = personService.getAllInSet();
        log.debug(LogUtil.makeList(all, 0, true));
        return all;
    }

    @GetMapping(path = "/single/person/{bid}")
    public PersonDto getSingle(@PathVariable("bid") String bid) {
        PersonDto single = personService.getSingle(bid);
        log.debug(single.toString());
        return single;
    }

}
