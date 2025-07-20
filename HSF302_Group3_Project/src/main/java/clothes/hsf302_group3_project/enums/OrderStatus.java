package clothes.hsf302_group3_project.enums;

public enum OrderStatus {
    PAID,                 // Đã thanh toán (bắt buộc vì thanh toán online)
    CONFIRMED,            // Người bán xác nhận
    SHIPPING,             // Đơn hàng đang được giao
    DELIVERED,            // Đã giao hàng
    COMPLETED,            // Người mua xác nhận hoàn tất
    CANCELLED,            // Đơn bị hủy
    DELIVERY_FAILED       // Giao hàng thất bại
}

