# Project Coding Rules (Non-Obvious Only)

- Always validate user ownership in service layer methods (findById checks if transaction belongs to user)
- Use constructor injection instead of @Autowired fields for better testability
- Entities must implement proper Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
- Custom exceptions (ResourceNotFoundException, ForbiddenException) must be used instead of generic ones
- Repository interfaces must extend JpaRepository and add custom query methods with proper @Param annotations
- Date range queries must use YearMonth.atDay(1) and YearMonth.atEndOfMonth() for consistency
- Controller methods must return ResponseEntity<T> for explicit HTTP status codes
- Use @Valid for request body validation in controllers
- Static files must be placed in src/main/resources/static/ with proper CSS/JS subdirectories