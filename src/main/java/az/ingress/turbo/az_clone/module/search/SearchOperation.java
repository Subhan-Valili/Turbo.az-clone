package az.ingress.turbo.az_clone.module.search;

public enum SearchOperation {
    EQUAL,                  // Tam bərabərlik (Məs: transmission = 'Avtomat')
    GREATER_THAN_EQUAL,     // Böyükdür və ya bərabərdir (Məs: priceMin, yearMin)
    LESS_THAN_EQUAL,        // Kiçikdir və ya bərabərdir (Məs: priceMax, yearMax)
    LIKE,                   // Mətndə qismən axtarış (Məs: description daxilində söz tapmaq)
    NOT_EQUAL               // Bərabər olmayanlar (Ehtiyac olarsa gələcək üçün)
}
