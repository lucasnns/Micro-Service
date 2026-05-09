package net.javaguides.user_service.dto;

public class ResponseDto {
    private UserDto user;
    private DepartmentDto department;

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public DepartmentDto getDepartment() { return department; }
    public void setDepartment(DepartmentDto department) { this.department = department; }
}
