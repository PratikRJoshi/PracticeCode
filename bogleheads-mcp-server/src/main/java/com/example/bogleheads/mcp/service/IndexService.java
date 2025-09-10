package com.example.bogleheads.mcp.service;

import com.example.bogleheads.mcp.model.ContextChunk;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Minimal Lucene wrapper.  Currently, keeps everything in RAM; later we can swap for FSDirectory.
 */
@Service
public class IndexService {
    private final Directory directory = new ByteBuffersDirectory();
    private final Analyzer analyzer = new StandardAnalyzer();
    private IndexWriter writer;

    public IndexService() throws Exception {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(directory, config);
    }

    public void indexChunk(ContextChunk chunk) throws Exception {
        Document doc = new Document();
        doc.add(new StringField("id", chunk.id(), Field.Store.YES));
        doc.add(new TextField("text", chunk.text(), Field.Store.YES));
        doc.add(new StringField("url", chunk.sourceUrl(), Field.Store.YES));
        writer.addDocument(doc);
        writer.commit();
    }

    public List<ContextChunk> search(String queryString, int limit) throws Exception {
        List<ContextChunk> out = new ArrayList<>();
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("text", analyzer);
            Query query = parser.parse(queryString);
            TopDocs topDocs = searcher.search(query, limit);
            for (ScoreDoc sd : topDocs.scoreDocs) {
                Document d = searcher.storedFields().document(sd.doc);
                out.add(new ContextChunk(
                        d.get("id"),
                        d.get("text"),
                        d.get("url")
                ));
            }
        }
        return out;
    }
}
