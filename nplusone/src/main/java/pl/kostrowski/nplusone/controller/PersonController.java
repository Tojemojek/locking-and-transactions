package pl.kostrowski.nplusone.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kostrowski.nplusone.model.Person;
import pl.kostrowski.nplusone.service.PersonService;
import pl.kostrowski.nplusone.util.LogUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonController {

  private final PersonService personService;

  @GetMapping(path = "/all/nonplus/people")
  public List<Person> getAllWithoutNPlusOne(@RequestParam("pageNo") Integer pageNo,
                                            @RequestParam("pageSize") Integer pageSize) {
    List<Person> all = personService.getAllWithoutNPlusOne(
        PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "bid"));
    log.debug(LogUtil.makeList(all, 0, true));
    return all;
  }

  @GetMapping(path = "/all/nplus/people")
  public List<Person> getAllWittNPlusOne(@RequestParam("pageNo") Integer pageNo,
                                         @RequestParam("pageSize") Integer pageSize) {
    List<Person> all = personService.getAllNPlusOne(
        PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "bid"));
    log.debug(LogUtil.makeList(all, 0, true));
    return all;
  }

  @GetMapping(path = "/all/people")
  public List<Person> getAll() {
    List<Person> all = personService.getAll();
    log.debug(LogUtil.makeList(all, 0, true));
    return all;
  }

  @GetMapping(path = "/all/people/set")
  public List<Person> getAllInSet() {
    List<Person> all = personService.getAllInSet();
    log.debug(LogUtil.makeList(all, 0, true));
    return all;
  }

  @GetMapping(path = "/single/person/{bid}")
  public Person getSingle(@PathVariable("bid") String bid) {
    Person single = personService.getSingle(bid);
    log.debug(single.toString());
    return single;
  }

}
