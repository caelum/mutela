Mutela is an API to get the Most Tweeted Links and other pretty simple Strings and links manipulation.

O algoritmo para extrair o texto é bastante parecido com o do Readability:
http://lab.arc90.com/experiments/readability/

O codigo javascript é este:
http://github.com/peas/readability-redux/blob/master/readability/readability.js

Basicamente, pegamos as tags de texto (como P) e damos uma nota pra tag, baseada em numero de virgulas, e se tem palavras boas no seu ID e no seu CLASS, como "article" ou "entry", e tiramos pontos se tem "comments", etc. Tambem precisa ver se tem link demais. Se tiver, perde pontos. Esses pontos vao pra tag pai (parentElement) e metade deles para o avo.

Para parsear o HTML, mesmo mal formado, usamos o jericho:
http://jericho.htmlparser.net/docs/index.html

