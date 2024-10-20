package com.api.simple_api.service.impl;

import com.api.simple_api.dto.EmployeeDto;
import com.api.simple_api.entity.Employee;
import com.api.simple_api.exception.ResourceNotFoundException;
import com.api.simple_api.mapper.EmployeeMapper;
import com.api.simple_api.repository.EmployeeRepository;
import com.api.simple_api.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee is not exists with given id"));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employess = employeeRepository.findAll();
        return employess.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployeeDto) {
       Employee employee = employeeRepository.findById(employeeId).orElseThrow(
               () -> new ResourceNotFoundException("Employee doesn't exist with given id: " + employeeId)
       );

       employee.setFirstName(updatedEmployeeDto.getFirstName());
       employee.setLastName(updatedEmployeeDto.getLastName());
       employee.setEmail(updatedEmployeeDto.getEmail());

       Employee updatedEmployeeObj = employeeRepository.save(employee);

       return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exists with given id")
        );

        employeeRepository.deleteById(employeeId);
    }
}
