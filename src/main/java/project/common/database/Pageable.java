package project.common.database;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pageable<T> {
    private List<T> data;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageSize;
    private Integer currentPage;
}
