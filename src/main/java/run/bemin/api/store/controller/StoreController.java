package run.bemin.api.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.store.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@RestController
public class StoreController {

  private final StoreService storeService;


}
