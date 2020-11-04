package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.dao.PromotionRepo;
import com.example.centrumtelefonii.models.Click;
import com.example.centrumtelefonii.models.Promotion;
import com.example.centrumtelefonii.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class PromotionController {

    PromotionRepo promotionRepo;

    public PromotionController( PromotionRepo promotionRepo) {
        this.promotionRepo = promotionRepo;
    }

    @Scheduled(cron = "* 0 0 * * *")
    public void deleteOldPromotions() {
        LocalDate localDate = LocalDate.now();
        List<Promotion>promotions = promotionRepo.findAll();
        for (Promotion promotion : promotions) {
            if(promotion.getDate().isBefore(localDate)){
                promotionRepo.delete(promotion);
            }
        }
    }


    @GetMapping("/promocje")
    public List<Promotion> getPromotions(){
        return promotionRepo.findAllSorted();
    }

    @GetMapping("/promocje/{pid}")
    public Optional<Promotion> getPromotion(@PathVariable("pid") int pid){
        return promotionRepo.findById(pid);
    }


    @GetMapping("/promocja/dzisiaj")
    public Optional<Promotion> getPromotionByDate() {
        return promotionRepo.findByDate(LocalDate.now());
    }

    @PutMapping(value = "/promocja", consumes = "application/json")
    public Promotion updatePromotion(@RequestBody Promotion Promotion) {
        promotionRepo.save(Promotion);
        return Promotion;
    }

    @DeleteMapping("/promocje/{pid}")
    public ResponseEntity<ApiResponse> deletePromotion(@PathVariable("pid") int pid) {
        Promotion a = promotionRepo.getOne(pid);
        promotionRepo.delete(a);
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie usunięto promocje"));
    }

    @DeleteMapping("/promocje")
    public ResponseEntity<ApiResponse> deleteAllPromotion(){
        promotionRepo.deleteAll();
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie usunięto wszystkie promocje"));
    }


    @PostMapping(path="/promocja", consumes="application/json")
    public Promotion addPromotion(@RequestBody Promotion promotion) {
        promotionRepo.save(promotion);
        return promotion;
    }
}
