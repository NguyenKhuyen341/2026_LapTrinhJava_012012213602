package com.planbookai.backend.domain.model.assessment;

import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class CauHoiSpecifications {

    private CauHoiSpecifications() {
    }

    public static Specification<CauHoi> coTuKhoa(String tuKhoa) {
        return (root, query, builder) -> {
            if (tuKhoa == null || tuKhoa.isBlank()) {
                return builder.conjunction();
            }

            String mauTimKiem = "%" + tuKhoa.trim().toLowerCase(Locale.ROOT) + "%";
            return builder.or(
                    builder.like(builder.lower(root.get("noiDungCauHoi")), mauTimKiem),
                    builder.like(builder.lower(root.get("chuDe")), mauTimKiem)
            );
        };
    }

    public static Specification<CauHoi> thuocChuDe(String chuDe) {
        return (root, query, builder) -> {
            if (chuDe == null || chuDe.isBlank()) {
                return builder.conjunction();
            }

            return builder.equal(
                    builder.lower(root.get("chuDe")),
                    chuDe.trim().toLowerCase(Locale.ROOT)
            );
        };
    }

    public static Specification<CauHoi> coMucDo(MucDoKho mucDoKho) {
        return (root, query, builder) -> mucDoKho == null
                ? builder.conjunction()
                : builder.equal(root.get("mucDoKho"), mucDoKho);
    }
}
