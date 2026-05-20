package com.donation.controller;

import com.donation.model.Donation;
import com.donation.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class DonationController {

    @Autowired
    private DonationRepository donationRepository;

    @GetMapping
    public String index(Model model) {
        List<Donation> donations = donationRepository.findAll();
        double totalDonation = donations.stream()
                .mapToDouble(Donation::getDonation)
                .sum();
        model.addAttribute("donations", donations);
        model.addAttribute("totalDonation", totalDonation);
        model.addAttribute("donation", new Donation());
        return "index";
    }

    @PostMapping("/add")
    public String addDonation(@ModelAttribute Donation donation) {
        donation.setPaymentMethod("QR Code");
        donation.setPaymentStatus("Paid");
        donationRepository.save(donation);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateDonation(@PathVariable Long id, @ModelAttribute Donation donation) {
        Donation existing = donationRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(donation.getName());
            existing.setEmail(donation.getEmail());
            existing.setMobile(donation.getMobile());
            existing.setDonation(donation.getDonation());
            existing.setPaymentStatus("Paid");
            donationRepository.save(existing);
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteDonation(@PathVariable Long id) {
        donationRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/api/donations")
    @ResponseBody
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @PostMapping("/api/donations")
    @ResponseBody
    public Donation createDonation(@RequestBody Donation donation) {
        donation.setPaymentMethod("QR Code");
        donation.setPaymentStatus("Paid");
        return donationRepository.save(donation);
    }

    @GetMapping("/api/donations/{id}")
    @ResponseBody
    public Donation getDonation(@PathVariable Long id) {
        return donationRepository.findById(id).orElse(null);
    }

    @PutMapping("/api/donations/{id}")
    @ResponseBody
    public Donation updateDonationAPI(@PathVariable Long id, @RequestBody Donation donation) {
        Donation existing = donationRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(donation.getName());
            existing.setEmail(donation.getEmail());
            existing.setMobile(donation.getMobile());
            existing.setDonation(donation.getDonation());
            existing.setPaymentStatus("Paid");
            return donationRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/api/donations/{id}")
    @ResponseBody
    public void deleteDonationAPI(@PathVariable Long id) {
        donationRepository.deleteById(id);
    }
}
