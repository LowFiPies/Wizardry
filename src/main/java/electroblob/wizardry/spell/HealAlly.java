package electroblob.wizardry.spell;

import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HealAlly extends SpellRay {

	public HealAlly(){
		super("heal_ally", false, EnumAction.NONE);
		this.soundValues(0.7f, 1.2f, 0.4f);
		addProperties(HEALTH);
	}

	@Override
	protected boolean onEntityHit(World world, Entity target, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers){
		
		if(WizardryUtilities.isLiving(target)){
			
			EntityLivingBase entity = (EntityLivingBase)target;
			
			if(entity.getHealth() < entity.getMaxHealth() && entity.getHealth() > 0){
				
				entity.heal(getProperty(HEALTH).floatValue() * modifiers.get(SpellModifiers.POTENCY));

				if(world.isRemote) ParticleBuilder.spawnHealParticles(world, entity);
				playSound(world, entity, ticksInUse, -1, modifiers);
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	protected boolean onBlockHit(World world, BlockPos pos, EnumFacing side, Vec3d hit, EntityLivingBase caster, Vec3d origin, int ticksInUse, SpellModifiers modifiers){
		return false;
	}

	@Override
	protected boolean onMiss(World world, EntityLivingBase caster, Vec3d origin, Vec3d direction, int ticksInUse, SpellModifiers modifiers){
		return false;
	}

}
