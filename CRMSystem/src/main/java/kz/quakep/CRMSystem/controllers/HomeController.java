package kz.quakep.CRMSystem.controllers;

import kz.quakep.CRMSystem.entities.Operator;
import kz.quakep.CRMSystem.entities.Request;
import kz.quakep.CRMSystem.repositories.CourseRepository;
import kz.quakep.CRMSystem.repositories.OperatorRepository;
import kz.quakep.CRMSystem.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping(value = "/")
    public String indexPage(Model model){
        List<Request> requests = requestRepository.findAll();
        model.addAttribute("requests", requests);
        model.addAttribute("courses", courseRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/handledApps")
    public String handledAppsPage(Model model){
        List<Request> requests = requestRepository.findAll();
        List<Request> handledRequests = new ArrayList<>();
        List<Request> newRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.isHandled()){
                handledRequests.add(request);
            }
        }
        for(Request request : requests){
            if(!request.isHandled()){
                newRequests.add(request);
            }
        }
        model.addAttribute("requests", requests);
        model.addAttribute("handledRequests", handledRequests);
        model.addAttribute("newRequests", newRequests);
        model.addAttribute("courses", courseRepository.findAll());
        return "handledApps";
    }

    @GetMapping(value = "/newApps")
    public String newAppsPage(Model model){
        List<Request> requests = requestRepository.findAll();
        List<Request> handledRequests = new ArrayList<>();
        List<Request> newRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.isHandled()){
                handledRequests.add(request);
            }
        }
        for(Request request : requests){
            if(!request.isHandled()){
                newRequests.add(request);
            }
        }
        model.addAttribute("requests", requests);
        model.addAttribute("handledRequests", handledRequests);
        model.addAttribute("newRequests", newRequests);
        model.addAttribute("courses", courseRepository.findAll());
        return "newApps";
    }

    @GetMapping(value = "/addApp")
    public String addAppGet(Model model){
        List<Request> requests = requestRepository.findAll();
        model.addAttribute("requests", requests);
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "addApp";
    }

    @PostMapping(value = "/addApp")
    public String addAppPost(Model model,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "number") String number,
                                 @RequestParam(name = "comment") String comment,
                                 @RequestParam(name = "courseId") Long id)
    {
        requestRepository.save(new Request(null, name ,comment, number, false, courseRepository.findById(id).orElse(null),null));
        List<Request> requests = requestRepository.findAll();
        model.addAttribute("requests", requests);
        model.addAttribute("courses", courseRepository.findAll());
        return "redirect:/";
    }

    @GetMapping(value = "/details/{id}")
    public String detailsPage(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("request", requestRepository.findById(id).orElse(null));
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("operators", operatorRepository.findAll());

        return "details";
    }

    @PostMapping(value = "/processApp/{id}")
    public String processApp(Model model, @PathVariable(name = "id") Long id, @RequestParam(name = "operatorIds", required = false) List<Long> operatorIds) {
        Request request = requestRepository.findById(id).orElse(null);

        if (request != null) {
            if (operatorIds != null) {
                // Update the request with selected operators
                List<Operator> selectedOperators = operatorRepository.findAllById(operatorIds);
                request.setOperators(selectedOperators);
            } else {
                // If operatorIds is null, use the existing operators
                List<Operator> existingOperators = request.getOperators();
                request.setOperators(existingOperators);
            }

            request.setHandled(true); // Mark the request as handled
            requestRepository.save(request);
        }

        // Redirect to the details page
        return "redirect:/details/" + id;
    }


    @PostMapping(value = "/removeOperator/{id}")
    public String removeOperator(
            Model model,
            @PathVariable(name = "id") Long requestId,
            @RequestParam(name = "operatorId") Long operatorId
    ) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request != null) {
            List<Operator> operators = request.getOperators();
            if (operators != null) {
                // Remove the operator by ID
                operators.removeIf(operator -> operator.getId().equals(operatorId));
                request.setOperators(operators);
                requestRepository.save(request);
                System.out.println("Operator removed: " + operatorId);
            }
        }

        // Redirect to the details page
        return "redirect:/details/" + requestId;
    }

}