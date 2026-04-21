package cl.privdata.complianceService.DTO.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;



public class ConsentCategoriesUpdateRequestDTO{

    @NotNull
    private List<UUID> categoryIds = new ArrayList<>();

    public ConsentCategoriesUpdateRequestDTO() {
    }

    public List<UUID> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<UUID> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
