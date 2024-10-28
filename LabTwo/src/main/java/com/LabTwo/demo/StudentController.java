package com.LabTwo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;
import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Display login and registration page
    @GetMapping("/")
    public String showIndexPage(Model model) {
        model.addAttribute("student", new Student());
        return "index";
    }

    // Handle login submission
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        Optional<Student> studentOptional = studentRepository.findByUserNameAndPassword(username, password);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            model.addAttribute("student", student);
            return "redirect:/home?studentId=" + student.getStudentID();
        } else {
            return "wrong"; // Redirect to the wrong.html page
        }
    }

    // Display the home page for the logged-in student
    @GetMapping("/home")
    public String showHome(@RequestParam Long studentId, Model model) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            model.addAttribute("student", studentOptional.get());
            return "home";
        } else {
            return "wrong"; // In case the student is not found
        }
    }

    // Display the profile page for the logged-in student
    @GetMapping("/profile")
    public String showProfile(@RequestParam Long studentId, Model model) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            model.addAttribute("student", studentOptional.get());
            return "profile";
        } else {
            return "wrong";
        }
    }

    // Display the program selection page
    @GetMapping("/program")
    public String showProgram(@RequestParam Long studentId, Model model) {
        model.addAttribute("studentId", studentId);
        return "program";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String address,
                           @RequestParam String city,
                           @RequestParam String postalCode,
                           @RequestParam String technicalSkills,
                           Model model) {
        Student newStudent = new Student(userName, password, firstName, lastName, address, city, postalCode, technicalSkills);
        studentRepository.save(newStudent);
        return "registered";
    }

    // Handle program enrollment
 // Handle program enrollment and ensure the program exists
    @PostMapping("/enroll")
    public String enroll(@RequestParam Long studentId,
                         @RequestParam String program,
                         Model model) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            // Check if the program already exists in the database
            Program selectedProgram = programRepository.findById(program).orElse(null);
            
            if (selectedProgram == null) {
                // If the program does not exist, add it to the Program table
                if (program.equals("SE01")) {
                    selectedProgram = new Program("SE01", "Software Engineering Technology", 3, 1050.00, "Dr. Johnson");
                } else if (program.equals("GD01")) {
                    selectedProgram = new Program("GD01", "Game Development", 4, 893.00, "Dr. Smith");
                }

                // Save the new program to the database
                programRepository.save(selectedProgram);
            }

            // Proceed with enrollment logic if the selected program is valid
            if (selectedProgram != null) {
                boolean alreadyEnrolled = enrollmentRepository
                    .findByStudentAndProgram(student, selectedProgram)
                    .isPresent();

                if (!alreadyEnrolled) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setStudent(student);
                    enrollment.setProgram(selectedProgram);
                    enrollment.setStartDate(new Date());
                    enrollment.setAmountPaid(selectedProgram.getFee());
                    enrollmentRepository.save(enrollment);
                } else {
                    model.addAttribute("message", "You are already enrolled in this program.");
                }
            } else {
                model.addAttribute("message", "Selected program not found.");
            }

            model.addAttribute("student", student);
            return "enrolled"; // Redirect to enrolled.html after successful enrollment
        } else {
            return "wrong"; // In case the student ID is not found
        }
    }
    
 // Handle profile update submission
    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam Long studentId,
                                @RequestParam String address,
                                @RequestParam String city,
                                @RequestParam String postalCode,
                                @RequestParam String technicalSkills,
                                Model model) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setAddress(address);
            student.setCity(city);
            student.setPostalCode(postalCode);
            student.setTechnicalSkills(technicalSkills);

            studentRepository.save(student); // Save the updated student info

            model.addAttribute("student", student);
            return "profile"; // Redirect back to the profile page with updated info
        } else {
            return "wrong"; // In case the student ID is not found
        }
    }

}
