package no.trinnvis.dabih;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.trinnvis.dabih.auth.UserAuthenticator;
import no.trinnvis.dabih.core.AuthenticatedUser;
import no.trinnvis.dabih.db.*;
import no.trinnvis.dabih.resources.*;
import no.trinnvis.dabih.services.*;
import no.trinnvis.dabih.util.DateUtil;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.hibernate.cfg.Configuration;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.TimeZone;
import java.util.logging.Logger;

public class DabihApiApplication extends Application<DabihApiConfiguration> {

    private final HibernateBundle<DabihApiConfiguration> hibernateBundle = new DabihHibernateBundle("no.trinnvis.dabih.domain");

    public static void main(String[] args) throws Exception {

        new DabihApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "sample";
    }

    @Override
    public void initialize(Bootstrap<DabihApiConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new DabihMigrationsBundle());
        bootstrap.addBundle(hibernateBundle);
        TimeZone.setDefault(DateUtil.timeZone);
    }

    @Override
    public void run(DabihApiConfiguration configuration,
        Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "sample-database");

        final AccountDAO dao = new AccountDAO(hibernateBundle.getSessionFactory());
        final UserStore userStore = new UserDAO(hibernateBundle.getSessionFactory());

        final TransferService transferDBService = new TransferService();

        configureCors(environment);

        environment.getObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        environment.jersey().register(new LoggingFilter(
            Logger.getLogger(LoggingFilter.class.getName()),
            true)
        );

        UserAuthenticator userAuthenticator =  new UnitOfWorkAwareProxyFactory(hibernateBundle).create(UserAuthenticator.class, new Class[]{UserStore.class}, new Object[]{userStore});

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<AuthenticatedUser>()
                .setAuthenticator(userAuthenticator)
                .setRealm("sample.no (api)")
                .buildAuthFilter()));

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(AuthenticatedUser.class));

        environment.jersey().register(new AccountsResource(dao, userStore));
        environment.jersey().register(new AccountDocumentResource(dao));
        environment.jersey().register(MultiPartFeature.class);
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("exposedHeaders", "Accusoft-Data-Encrypted,Accusoft-Data-SK,Accusoft-Status-Message,Accusoft-Status-Number");
        filter.setInitParameter("allowCredentials", "true");
    }

    static class DabihHibernateBundle extends ScanningHibernateBundle<DabihApiConfiguration> {

        protected DabihHibernateBundle(String pckg) {
            super(pckg);
        }

        @Override
        public DataSourceFactory getDataSourceFactory(DabihApiConfiguration dabihApiConfiguration) {
            return dabihApiConfiguration.getDataSourceFactory();
        }

        @Override
        protected void configure(Configuration configuration) {
            configuration.setImplicitNamingStrategy(new ImplicitNamingStrategyImpl());
            configuration.setPhysicalNamingStrategy(new PhysicalNamingStrategyImpl());
            super.configure(configuration);
        }
    }

    static class DabihMigrationsBundle extends MigrationsBundle<DabihApiConfiguration> {
        @Override
        public DataSourceFactory getDataSourceFactory(DabihApiConfiguration dabihApiConfiguration) {
            return dabihApiConfiguration.getDataSourceFactory();
        }
    }
}
