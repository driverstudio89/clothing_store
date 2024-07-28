package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.enums.StatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeStatusDto {
    private StatusType status;
}
