# BlockLimiter 方块限制

该插件仅支持 [StarWishsama 的汉化版 Slimefun](https://github.com/StarWishsama/Slimefun4)。  
基于 [HeadLimiter](https://github.com/Slimefun-Addon-Community/HeadLimiter) 重写。

本插件可以限制每个区块的粘液方块数量，以防止玩家在某个区块内放置过多粘液方块导致服务器卡顿。  
限制仅对新放置的方块生效，不会影响已有方块。因此，建议在开服时就添加该插件并针对服务器性能进行适当配置。

- 将相似方块进行编组来进行限制（虽然你可以任意进行编组）
- 额外权限节点`blocklimiter.permission.<permission_node>`来单独修改限制（如需禁用限制，请设置值为较大的数值）。拥有多个权限节点时，取最大值。
- `blocklimiter.bypass` 权限节点可以忽略所有限制
- 支持热重载配置（`/bl reload`）
- 使用`/bl count`查看当前当前区块的限制

## 下载

[![点击前往下载](https://builds.guizhanss.com/api/badge/ybw0014/BlockLimiter/master/latest)](https://builds.guizhanss.com/ybw0014/BlockLimiter/master)
