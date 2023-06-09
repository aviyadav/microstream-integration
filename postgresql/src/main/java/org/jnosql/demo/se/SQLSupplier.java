package org.jnosql.demo.se;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import one.microstream.afs.sql.types.SqlConnector;
import one.microstream.afs.sql.types.SqlFileSystem;
import one.microstream.afs.sql.types.SqlProviderPostgres;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.function.Supplier;
@Alternative
@Priority(Interceptor.Priority.APPLICATION)
@ApplicationScoped
class SQLSupplier implements Supplier<StorageManager> {

    @Inject
    private DataSource dataSource;

    @Override
    @Produces
    @ApplicationScoped
    public StorageManager get() {
        SqlFileSystem fileSystem = SqlFileSystem.New(
                SqlConnector.Caching(
                        SqlProviderPostgres.New(dataSource)
                )
        );
        return EmbeddedStorage.start(fileSystem.ensureDirectoryPath("microstream_storage"));
    }

    public void close(@Disposes StorageManager manager) {
        manager.close();
    }
}
