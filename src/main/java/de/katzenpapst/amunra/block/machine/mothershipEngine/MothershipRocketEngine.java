package de.katzenpapst.amunra.block.machine.mothershipEngine;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.block.BlockMachineMeta;
import de.katzenpapst.amunra.item.ARItems;
import de.katzenpapst.amunra.item.ItemDamagePair;
import de.katzenpapst.amunra.tile.TileEntityIsotopeGenerator;
import de.katzenpapst.amunra.tile.TileEntityMothershipEngine;
import de.katzenpapst.amunra.world.CoordHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MothershipRocketEngine extends MothershipEngineBase {

    private IIcon iconFront = null;
    private IIcon iconBack = null;

    private String textureFront;
    private String textureBack;

    protected ItemDamagePair item = null;

    public MothershipRocketEngine(String name, String texture, String textureFront, String textureBack) {
        super(name, texture);
        this.textureFront = textureFront;
        this.textureBack = textureBack;
    }

    @Override
    public int getStrength() {
        return 1000;
    }

    @Override
    public double getSpeed() {
        return 0.5D;
    }

    @Override
    public boolean canTravelDistance(double distance) {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        super.registerBlockIcons(par1IconRegister);
        iconFront = par1IconRegister.registerIcon(textureFront);
        iconBack = par1IconRegister.registerIcon(textureBack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        int realMeta = ((BlockMachineMeta)this.parent).getRotationMeta(meta);
        // we have the front thingy at front.. but what is front?
        // east is the output
        // I think front is south
        ForgeDirection front = CoordHelper.rotateForgeDirection(ForgeDirection.SOUTH, realMeta);
        ForgeDirection back = CoordHelper.rotateForgeDirection(ForgeDirection.NORTH, realMeta);


        if(side == front.ordinal()) {
            return this.iconFront;
        }
        if(side == back.ordinal()) {
            return this.iconBack;
        }
        return this.blockIcon;
    }

    @Override
    public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ROCKET_ENGINE, world, x, y, z);
        return true;
        // return false;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityMothershipEngine();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
        return AmunRa.dummyRendererId;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return item.getItem();
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        /**
         * Returns whether or not this bed block is the head of the bed.
         */
        return item.getItem();
    }

    @Override
    protected ItemDamagePair getItem() {
        if(item == null) {
            item = new ItemDamagePair(ARItems.jetItem, 0);
        }
        return item;
    }

    @Override
    public int damageDropped(int meta) {
        return item.getDamage();
    }

}
