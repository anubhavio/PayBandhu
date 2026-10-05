package paybandhu.integration.aeps;

public interface AepsProvider {

    AepsProviderResponse balanceEnquiry(
            AepsProviderRequest request
    );
}
