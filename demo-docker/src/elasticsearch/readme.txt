一. sh run.sh

二. 初始化ik分词器
curl -XPUT http://localhost:9200/51ishare?pretty

curl -XPOST http://localhost:9200/51ishare/goodscommon/_mapping?pretty -d '
{
    "goodscommon": {
             "_all": {
            "analyzer": "ik_max_word",
            "search_analyzer": "ik_max_word",
            "term_vector": "no",
            "store": "false"
        },
        "properties": {
            "name": {
                "type": "string",
                "store": "no",
                "term_vector": "with_positions_offsets",
                "analyzer": "ik_max_word",
                "search_analyzer": "ik_max_word",
                "include_in_all": "true",
                "boost": 8
            }
        }
    }
}'