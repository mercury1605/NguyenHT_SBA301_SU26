package sba301.fu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sba301.fu.pojo.Student;
import sba301.fu.repository.IStudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentServiceTest {

    private IStudentRepository mockRepo;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        // 1. Giả lập một thực thể Repository bằng Mockito
        mockRepo = Mockito.mock(IStudentRepository.class);
        // 2. Tiêm mockRepo vào Service thông qua Constructor mới tạo
        studentService = new StudentService(mockRepo);
    }

    @Test
    void save_shouldCallRepositorySave() {
        // Given (Chuẩn bị dữ liệu mẫu)
        Student student = new Student("test@fpt.edu.vn", "password123", "Nguyen", "An", 9);

        // When (Thực thi hành động qua tầng Service)
        studentService.save(student);

        // Then (Kiểm tra xem Service có thực sự gọi hàm save của Repository đúng 1 lần không)
        Mockito.verify(mockRepo, Mockito.times(1)).save(student);
    }

    @Test
    void findById_shouldReturnStudent_whenStudentExists() {
        // Given (Giả lập hành vi: khi gọi repo.findById(1) thì trả về đối tượng student mong muốn)
        Student sampleStudent = new Student(1, "test@fpt.edu.vn", "password123", "Nguyen", "An", 9);
        Mockito.when(mockRepo.findById(1)).thenReturn(sampleStudent);

        // When
        Student result = studentService.findById(1);

        // Then
        assertNotNull(result, "Kết quả trả về không được rỗng!");
        assertEquals("test@fpt.edu.vn", result.getEmail());
        assertEquals("Nguyen", result.getFirstName());
    }
}