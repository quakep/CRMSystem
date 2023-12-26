package kz.quakep.CRMSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course courseId;
    @ManyToMany
    @JoinTable(name = "request_operators", joinColumns = @JoinColumn(name = "requestId"), inverseJoinColumns = @JoinColumn(name = "operatorId"))
    private List<Operator> operators = new ArrayList<>();
}